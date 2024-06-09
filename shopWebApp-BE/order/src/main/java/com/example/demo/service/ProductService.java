package com.example.demo.service;

import com.example.demo.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.utils.URIBuilder;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final RestTemplate restTemplate;
    @Value("${product.service}")
    private String PRODUCT_URL;


    public ProductEntity getProduct(String uuid) {
        URI uri = null;
        try {
            uri = new URIBuilder(PRODUCT_URL).addParameter("uuid", uuid).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<?> response = restTemplate.getForEntity(uri, ProductEntity.class);
        if (response.getStatusCode().isError()) {
            return null;
        }
        return (ProductEntity) response.getBody();
    }
}

