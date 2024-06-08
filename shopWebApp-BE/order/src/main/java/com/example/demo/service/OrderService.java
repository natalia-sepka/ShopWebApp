package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.DeliverRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.translators.BasketItemDTOToOrderItems;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverRepository deliverRepository;
    private final BasketService basketService;
    private final ItemService itemService;
    private final BasketItemDTOToOrderItems basketItemDTOToItems;
    private final PayUService payUService;

    private Order save(Order order) {
        Deliver deliver = deliverRepository.findByUuid(order.getDeliver().getUuid()).orElseThrow(RuntimeException::new);
        StringBuilder stringBuilder = new StringBuilder("ORDER/")
                .append(orderRepository.count())
                .append("/")
                .append(LocalDate.now().getMonthValue()+1)
                .append("/")
                .append(LocalDate.now().getYear());

        order.setUuid(UUID.randomUUID().toString());
        order.setStatus(Status.PENDING);
        order.setOrders(stringBuilder.toString());
        order.setDeliver(deliver);
        return orderRepository.saveAndFlush(order);
    }

    public String createOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
        Order finalOrder = save(order);
        AtomicReference<String> result = new AtomicReference<>();
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("basket")).findFirst().ifPresentOrElse(value -> {
            ListBasketItemDTO basket = basketService.getBasket(value);
            List<OrderItems> items = new ArrayList<>();
            basket.getBasketProducts().forEach(item -> {
                OrderItems orderItems = basketItemDTOToItems.toOrderItems(item);
                orderItems.setOrder(finalOrder);
                orderItems.setUuid(UUID.randomUUID().toString());
                items.add(itemService.save(orderItems));
                basketService.removeBasket(value, item.getUuid());
            });
            result.set(payUService.createOrder(finalOrder, items));
            value.setMaxAge(0);
            response.addCookie(value);
        }, () -> {
            throw new RuntimeException();
        });
        return result.get();
        }
    }