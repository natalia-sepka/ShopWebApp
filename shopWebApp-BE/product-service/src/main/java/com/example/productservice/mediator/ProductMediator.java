package com.example.productservice.mediator;

import com.example.productservice.entity.ProductEntity;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;
    public ResponseEntity<?> getProduct(
            int page,
            int limit,
            String name,
            String category,
            Float maxPrice,
            Float minPrice,
            String data,
            String sort,
            String order
    ) {
        long totalCount = productService.countActiveProducts(name, category,maxPrice, minPrice);
        List<ProductEntity> product = productService.getProduct(
                name,
                category,
                maxPrice,
                minPrice,
                data,
                page,
                limit,
                sort,
                order
        );
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(product);
    }
}
