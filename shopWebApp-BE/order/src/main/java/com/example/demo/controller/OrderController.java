package com.example.demo.controller;

import com.example.demo.entity.OrderDTO;
import com.example.demo.mediator.OrderMediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMediator orderMediator;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order, HttpServletRequest request, HttpServletResponse response){
        return orderMediator.createOrder(order,request,response);
    }
}

