package com.example.basket.service;

import com.example.basket.entity.*;
import com.example.basket.exceptions.BasketItemDoesntExistException;
import com.example.basket.exceptions.NoBasketInfoException;
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
                        httpHeaders.add("X-Total-Count", String.valueOf(sum));
                    }, () -> {
                        Basket basket = createBasket();
                        response.addCookie(cookieService.generateCookie("basket", basket.getUuid()));
                        addProductToBasket(basket, basketItemAddDTO);
                        Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                        if (sum == null) sum = 0L;
                        httpHeaders.add("X-Total-Count", String.valueOf(sum));
                    });
                }, () -> {
                    Basket basket = createBasket();
                    response.addCookie(cookieService.generateCookie("basket", basket.getUuid()));
                    addProductToBasket(basket, basketItemAddDTO);
                    Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                    if (sum == null) sum = 0L;
                    httpHeaders.add("X-Total-Count", String.valueOf(sum));
                });
        return ResponseEntity.ok().headers(httpHeaders).body(new Response("Successfully added item to the basket"));
    }

    private Basket createBasket() {
        Basket basket = new Basket();
        basket.setUuid(UUID.randomUUID().toString());
        return basketRepository.saveAndFlush(basket);
    }

    private void addProductToBasket(Basket basket, BasketItemAddDTO basketItemAddDTO) {
        System.out.println("add to basket");
        BasketItems basketItems = new BasketItems();
        try {
            Product product = getProduct(basketItemAddDTO.getProduct());
            if (product != null) {
                basketItemsRepository.findByBasketAndProduct(basket, product.getUid()).ifPresentOrElse(basketItems1 -> {
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

    public ResponseEntity<Response> delete(String uuid, HttpServletRequest request) throws NoBasketInfoException {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Cookie> cookies = new ArrayList<>();
        if (request.getCookies() != null) {
            cookies.addAll(List.of(request.getCookies()));
        }
        cookies.stream().filter(value -> value.getName().equals("basket"))
                .findFirst().ifPresentOrElse(value -> {
                    basketRepository.findByUuid(value.getValue()).ifPresentOrElse(basket -> {
                        deleteItem(uuid,basket);
                        Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                        if (sum == null) sum = 0L;
                        httpHeaders.add("X-Total-Count", String.valueOf(sum));
                    }, () -> {
                        throw new NoBasketInfoException("Basket doesn't exist");
                    });
                }, () -> {
                    throw new NoBasketInfoException("No basket info found in request");
                });
        return ResponseEntity.ok().headers(httpHeaders).body(new Response("Item from the basket successfully deleted."));
    }

    private void deleteItem(String uuid, Basket basket) throws BasketItemDoesntExistException {
        basketItemsRepository.findBasketItemsByProduct(uuid).ifPresentOrElse(basketItemsRepository::delete, () -> {
            throw new BasketItemDoesntExistException("Basket item doesn't exist");
        });
        Long sum = basketItemsRepository.sumBasketItems(basket.getId());
        if (sum == null || sum == 0) basketRepository.delete(basket);
    }

    public ResponseEntity<?> getItems(HttpServletRequest request) {
        List<Cookie> cookies = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (request.getCookies() != null) {
            cookies.addAll(List.of(request.getCookies()));
        }
        BasketItemListDTO basketItemListDTO = new BasketItemListDTO();
        basketItemListDTO.setBasketProducts(new ArrayList<>());
        cookies.stream().filter(value -> value.getName().equals("basket"))
                .findFirst().ifPresentOrElse(value -> {
                    Basket basket = basketRepository.findByUuid(value.getValue()).orElseThrow(NoBasketInfoException::new);
                    Long sum = basketItemsRepository.sumBasketItems(basket.getId());
                    if (sum == null) sum = 0L;
                    httpHeaders.add("X-Total-Count", String.valueOf(sum));
                    basketItemsRepository.findBasketItemsByBasket(basket).forEach(item -> {
                        try {
                            Product product = getProduct(item.getProduct());
                            basketItemListDTO.getBasketProducts().add(new BasketItemDTO(product.getUid(),
                                    product.getName(),
                                    item.getQuantity(),
                                    product.getImageUrls()[0],
                                    product.getPrice(),
                                    product.getPrice() * item.getQuantity()));
                            basketItemListDTO.setSummaryPrice(basketItemListDTO.getSummaryPrice() + (item.getQuantity() * product.getPrice()));
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    }, () -> {
                        throw new NoBasketInfoException("No basket info found in request");
                    });
                    if (httpHeaders.isEmpty()) httpHeaders.add("X-Total-Count", String.valueOf(0));
                    return ResponseEntity.ok().headers(httpHeaders).body(basketItemListDTO);
    }

}
