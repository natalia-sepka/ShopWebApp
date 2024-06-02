package com.example.demo.mediator;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.translators.OrderDTOToOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMediator {

    private final OrderDTOToOrder orderDTOToOrder;
    private final OrderService orderService;

    public ResponseEntity<?> createOrder(OrderDTO orderDTO) {
        Order order = orderDTOToOrder.toOrder(orderDTO);
        orderService.createOrder(order);
        return null;
    }
}

