package com.example.productservice.mediator;

import com.example.productservice.entity.*;
import com.example.productservice.exceptions.CategoryDoesntExistException;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import com.example.productservice.translator.ProductEntityToProductDTO;
import com.example.productservice.translator.ProductEntityToSimpleProduct;
import com.example.productservice.translator.ProductFormToProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final CategoryService categoryService;
    private final ProductEntityToSimpleProduct productEntityToSimpleProduct;
    private final ProductEntityToProductDTO productEntityToProductDTO;
    private final ProductFormToProductEntity productFormToProductEntity;

    @Value("${file-service.url}")
    private String FILE_SERVICE;

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
        product.forEach( value -> {
            for (int i = 0; i < value.getImageUrls().length; i++) {
                value.getImageUrls()[i] = FILE_SERVICE + "?uuid=" + value.getImageUrls()[i];
            }
        });
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

    public ResponseEntity<Response> saveProduct(ProductFormDTO productFormDTO) {
        try {
            ProductEntity product = productFormToProductEntity.toProductEntity(productFormDTO);
            categoryService.findCategoryByShortId(product.getCategory().getShortId()).ifPresentOrElse(product::setCategory, () -> {
                throw new CategoryDoesntExistException();
            });
            productService.createProduct(product);
            return ResponseEntity.ok(new Response("Product created."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response("Can't create product. Category doesn't exist."));
        }
    }

    public ResponseEntity<Response> deleteProduct(String uuid) {
        try {
            productService.delete(uuid);
            return ResponseEntity.ok(new Response("Product successfully deleted"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response("Product doesn't exist"));
        }
    }
}
