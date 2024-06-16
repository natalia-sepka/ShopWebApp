package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.PayUException;
import com.example.demo.translators.OrderItemsToPayuProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class PayUService {

    private final OrderItemsToPayuProduct orderItemsToPayuProduct;
    private final RestTemplate restTemplate;
    @Value("${payu.client-id}")
    private String client_id;
    @Value("${payu.client-secret}")
    private String client_secret;
    @Value("${payu.url.auth}")
    private String payu_url_auth;
    @Value("${payu.url.order}")
    private String payu_url_order;
    private String token;

    private void login(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type","client_credentials");
        map.add("client_id",client_id);
        map.add("client_secret",client_secret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<PayUAuth> response =
                restTemplate.exchange(payu_url_auth,
                        HttpMethod.POST,
                        entity,
                        PayUAuth.class);
        if (response.getStatusCode().isError()) throw new PayUException();
        token = "Bearer "+response.getBody().getAccess_token();
    }


    public String createOrder(Order finalOrder, List<OrderItems> items){
        try{
            return (String) sendOrder(finalOrder,items).getBody();
        }catch (HttpClientErrorException e){
            if (e.getStatusCode().value() == 401){
                login();
                return (String) sendOrder(finalOrder,items).getBody();
            }
        }
        return null;
    }

    private ResponseEntity<?> sendOrder(Order finalOrder, List<OrderItems> items){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",token);

        List<PayUProduct> products = items.stream().map(orderItemsToPayuProduct::toPayuProduct).toList();
        long totalPrice = (long) (finalOrder.getDeliver().getPrice() * 100);
        for (PayUProduct product : products) {
            totalPrice += product.getUnitPrice() * product.getQuantity() * 100;
        }
        PayUBuyer buyer = new PayUBuyer(finalOrder.getEmail(),finalOrder.getPhone(),finalOrder.getFirstName(), finalOrder.getLastName());
        PayUOrder payUOrder = new PayUOrder("https://your.eshop.com/notify","127.0.0.1",client_id,finalOrder.getOrders(),"PLN",totalPrice,finalOrder.getOrders(),buyer,products);
        HttpEntity<PayUOrder> requestEntity =
                new HttpEntity<>(payUOrder, headers);
        return restTemplate.exchange(payu_url_order,HttpMethod.POST,requestEntity,String.class);
    }


}

