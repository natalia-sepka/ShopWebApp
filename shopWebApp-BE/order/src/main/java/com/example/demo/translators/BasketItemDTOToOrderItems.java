package com.example.demo.translators;

import com.example.demo.entity.BasketItemDTO;
import com.example.demo.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class BasketItemDTOToOrderItems {


    public OrderItems toOrderItems(BasketItemDTO basketItemDTO){
        return translate(basketItemDTO);
    }


    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "product",source = "uuid"),
            @Mapping(target = "priceUnit",source = "price"),
            @Mapping(target = "priceSummary",source = "summaryPrice"),
    })
    protected abstract OrderItems translate(BasketItemDTO basketItemDTO);
}

