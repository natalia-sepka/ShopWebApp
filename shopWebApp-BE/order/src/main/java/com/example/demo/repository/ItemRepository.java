package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findOrderItemsByOrder(Order order);
}
