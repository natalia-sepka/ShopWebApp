package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Items {
    private String uuid;
    private String name;
    private String imageUrl;
    private long quantity;
    private double price;
    private double summaryPrice;
}
