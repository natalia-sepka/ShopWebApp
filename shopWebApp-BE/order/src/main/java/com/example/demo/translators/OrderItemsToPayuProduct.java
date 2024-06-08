package com.example.demo.translators;

import com.example.demo.entity.OrderItems;
import com.example.demo.entity.PayUProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class OrderItemsToPayuProduct {
    public PayUProduct toPayuProduct(OrderItems orderItems){
        return translate(orderItems);
    }


    @Mappings({
            @Mapping(source = "priceUnit",target = "unitPrice")
    })
    protected abstract PayUProduct translate(OrderItems orderItems);
}

