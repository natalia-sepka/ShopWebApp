package com.example.productservice.service;

import com.example.productservice.entity.ProductDTO;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO getProductDTO() {
        return null;
    }
}
