package com.example.productservice.facade;

import com.example.productservice.entity.ProductFormDTO;
import com.example.productservice.entity.Response;
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
            @RequestParam(required = false) String name_like,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String _category,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false, defaultValue = "1") int _page,
            @RequestParam(required = false, defaultValue = "10") int _limit,
            @RequestParam(required = false, defaultValue = "price") String _sort,
            @RequestParam(required = false, defaultValue = "asc") String _order
            ) {
        return productMediator.getProduct(_page, _limit, name_like, _category, minPrice, maxPrice, date, _sort, _order);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> save(@RequestBody ProductFormDTO productFormDTO) {
        return productMediator.saveProduct(productFormDTO);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam String uuid) {
        return productMediator.deleteProduct(uuid);
    }

    @RequestMapping(value = "getExternal", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String uuid) {
        return productMediator.getProductExtend(uuid);
    }
}
