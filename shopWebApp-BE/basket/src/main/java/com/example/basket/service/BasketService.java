package com.example.basket.service;

import com.example.basket.entity.*;
import com.example.basket.repository.BasketItemsRepository;
import com.example.basket.repository.BasketRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final RestTemplate restTemplate;
    private final CookieService cookieService;
    @Value("${product.service.url}")
    private String PRODUCT_URL;


    public ResponseEntity<?> add(BasketItemAddDTO basketItemAddDTO, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Cookie> cookies = new ArrayList<>();

        if (request.getCookies() != null) {
            cookies.addAll(List.of(request.getCookies()));
        }

        cookies.stream().filter(value -> value.getName().equals("basket"))
                .findFirst().ifPresentOrElse(value -> {
                    basketRepository.findByUuid(value.getValue()).ifPresentOrElse(basket -> {
                        addProductToBasket(basket, basketItemAddDTO);
                        Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                        if (sum == null) sum = 0L;
                        httpHeaders.add("X-TOTAL-COUNT", String.valueOf(sum));
                    }, () -> {
                        Basket basket = createBasket();
                        response.addCookie(cookieService.generateCookie("basket", basket.getUuid()));
                        addProductToBasket(basket, basketItemAddDTO);
                        Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                        if (sum == null) sum = 0L;
                        httpHeaders.add("X-TOTAL-COUNT", String.valueOf(sum));
                    });
                }, () -> {
                    Basket basket = createBasket();
                    response.addCookie(cookieService.generateCookie("basket", basket.getUuid()));
                    addProductToBasket(basket, basketItemAddDTO);
                    Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                    if (sum == null) sum = 0L;
                    httpHeaders.add("X-TOTAL-COUNT", String.valueOf(sum));
                });
        return ResponseEntity.ok().headers(httpHeaders).body(new Response("Successfully added item to the basket"));
    }

    private Basket createBasket() {
        Basket basket = new Basket();
        basket.setUuid(UUID.randomUUID().toString());
        return basketRepository.saveAndFlush(basket);
    }

    private void addProductToBasket(Basket basket, BasketItemAddDTO basketItemAddDTO) {
        BasketItems basketItems = new BasketItems();
        try {
            Product product = getProduct(basketItemAddDTO.getProduct());
            if (product != null) {
                basketItemsRepository.findByBasketAndProduct(basket, product.getId()).ifPresentOrElse(basketItems1 -> {
                    basketItems1.setQuantity(basketItems1.getQuantity() + basketItemAddDTO.getQuantity());
                    basketItemsRepository.save(basketItems1);
                }, () -> {
                    basketItems.setBasket(basket);
                    basketItems.setUuid(UUID.randomUUID().toString());
                    basketItems.setQuantity(basketItemAddDTO.getQuantity());
                    basketItems.setProduct(product.getUid());
                    basketItemsRepository.saveAndFlush(basketItems);
                });
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Product getProduct(String uuid) throws URISyntaxException {
        URI uri = new URIBuilder(PRODUCT_URL + "/getExternal").addParameter("uuid", uuid).build();
        ResponseEntity<?> response = restTemplate.getForEntity(uri, Product.class);
        if (response.getStatusCode().isError()) {
            return null;
        }
        return (Product) response.getBody();
    }
}
