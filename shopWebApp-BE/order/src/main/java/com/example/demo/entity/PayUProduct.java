package com.example.demo.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayUProduct {
    private String name;
    private long unitPrice;
    private long quantity;
}
