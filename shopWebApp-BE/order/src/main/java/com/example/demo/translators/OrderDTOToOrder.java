package com.example.demo.translators;

import com.example.demo.entity.Deliver;
import com.example.demo.entity.DeliverDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class OrderDTOToOrder {

    public Order toOrder(OrderDTO orderDTO){
        return translate(orderDTO);
    }


    @Mappings({
            @Mapping(expression = "java(orderDTO.getCustomerDetails().getFirstName())",target = "firstName"),
            @Mapping(expression = "java(orderDTO.getCustomerDetails().getLastName())",target = "lastName"),
            @Mapping(expression = "java(orderDTO.getCustomerDetails().getEmail())",target = "email"),
            @Mapping(expression = "java(orderDTO.getCustomerDetails().getPhone())",target = "phone"),
            @Mapping(expression = "java(orderDTO.getAddress().getCity())",target = "city"),
            @Mapping(expression = "java(orderDTO.getAddress().getNumber())",target = "number"),
            @Mapping(expression = "java(orderDTO.getAddress().getStreet())",target = "street"),
            @Mapping(expression = "java(orderDTO.getAddress().getPostCode())",target = "postCode"),
            @Mapping(expression = "java(translate(orderDTO.getDeliver()))",target = "deliver"),
    })
    protected abstract Order translate(OrderDTO orderDTO);


    @Mappings({})
    protected abstract Deliver translate(DeliverDTO deliverDTO);
}

