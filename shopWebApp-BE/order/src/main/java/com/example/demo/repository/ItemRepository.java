package com.example.demo.repository;

import com.example.demo.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<OrderItems, Long> {
}
