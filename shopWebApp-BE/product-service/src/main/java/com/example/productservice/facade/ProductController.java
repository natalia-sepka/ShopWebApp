package com.example.productservice.facade;

import com.example.productservice.mediator.ProductMediator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMediator productMediator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
            ) {
        productMediator.getProduct(page, limit);
        return null;
    }
}
