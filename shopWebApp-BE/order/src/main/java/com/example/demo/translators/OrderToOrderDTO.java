package com.example.demo.translators;

import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract  class OrderToOrderDTO {
    public OrderDTO toOrderDTO(Order order){
        return translate(order);
    }

    @Mappings({
            @Mapping(target = "customerDetails",expression = "java(translateToCustomer(order))"),
            @Mapping(target = "address",expression = "java(translateAddress(order))"),
            @Mapping(target = "deliver",expression = "java(translateDeliver(order.getDeliver()))"),
    })
    protected abstract OrderDTO translate(Order order);

    @Mappings({})
    protected abstract CustomerDetails translateToCustomer(Order order);

    @Mappings({})
    protected abstract Address translateAddress(Order order);

    @Mappings({})
    protected abstract CustomerDetails translateToCustomer(Deliver deliver);

}

