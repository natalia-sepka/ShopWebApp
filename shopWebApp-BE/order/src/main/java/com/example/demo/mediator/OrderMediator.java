package com.example.demo.mediator;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.translators.OrderDTOToOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMediator {

    private final OrderDTOToOrder orderDTOToOrder;
    private final OrderService orderService;

    public ResponseEntity<?> createOrder(OrderDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
        Order order = orderDTOToOrder.toOrder(orderDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return ResponseEntity.status(200).headers(httpHeaders).body(orderService.createOrder(order, request, response));
    }
}

