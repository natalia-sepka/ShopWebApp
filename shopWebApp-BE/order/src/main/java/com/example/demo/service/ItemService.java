package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItems;
import com.example.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public OrderItems save(OrderItems items){
        return itemRepository.saveAndFlush(items);
    }

    public List<OrderItems> getByOrder(Order order){
        return itemRepository.findOrderItemsByOrder(order);
    }

}

