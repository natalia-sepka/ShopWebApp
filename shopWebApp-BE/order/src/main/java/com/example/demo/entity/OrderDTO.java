package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private String uuid;
    private String orders;
    private Status status;
    private CustomerDetails customerDetails;
    private Address address;
    private DeliverDTO deliver;
    private Items items;
}
