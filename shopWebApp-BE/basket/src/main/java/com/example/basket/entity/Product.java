package com.example.basket.entity;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private long id;
    private String uid;
    private boolean activate;
    private String name;
    private String mainDesc;
    private String descHtml;
    private float price;
    private String[] imageUrls;
    private String parameters;
    private LocalDate createAt;
    private Category category;
}
