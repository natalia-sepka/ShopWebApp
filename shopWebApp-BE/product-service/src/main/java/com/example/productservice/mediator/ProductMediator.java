package com.example.productservice.mediator;

import com.example.productservice.entity.ProductDTO;
import com.example.productservice.entity.ProductEntity;
import com.example.productservice.entity.SimpleProductDTO;
import com.example.productservice.service.ProductService;
import com.example.productservice.translator.ProductEntityToProductDTO;
import com.example.productservice.translator.ProductEntityToSimpleProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;
    private final ProductEntityToSimpleProduct productEntityToSimpleProduct;
    private final ProductEntityToProductDTO productEntityToProductDTO;
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
        if (name != null && !name.isEmpty()) {
            try {
                name = URLDecoder.decode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        if (name == null || name.isEmpty() || data == null || data.isEmpty()) {
            List<SimpleProductDTO> simpleProductDTOS = new ArrayList<>();
            long totalCount = productService.countActiveProducts(name, category,maxPrice, minPrice);
            product.forEach(value -> {
                simpleProductDTOS.add(productEntityToSimpleProduct.toSimpleProduct(value));
            });
            return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(simpleProductDTOS);
        }
        ProductDTO productDTO = productEntityToProductDTO.toProduct(product.get(0));
        return ResponseEntity.ok().body(productDTO);
    }
}
