package com.example.basket.controller;


import com.example.basket.entity.BasketItemAddDTO;
import com.example.basket.entity.Response;
import com.example.basket.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(
            @RequestBody BasketItemAddDTO basketItemAddDTO,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        return this.basketService.add(basketItemAddDTO, request, response);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam String uuid, HttpServletRequest request) {
        return basketService.delete(uuid, request);
    }
}
