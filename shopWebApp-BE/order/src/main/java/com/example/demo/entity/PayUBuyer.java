package com.example.demo.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayUBuyer {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
