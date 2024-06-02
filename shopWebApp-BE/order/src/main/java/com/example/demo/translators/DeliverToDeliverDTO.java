package com.example.demo.translators;

import com.example.demo.entity.Deliver;
import com.example.demo.entity.DeliverDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class DeliverToDeliverDTO {
    public DeliverDTO deliverDTO(Deliver deliver){
        return translate(deliver);
    }

    @Mappings({})
    protected abstract DeliverDTO translate(Deliver deliver);

}
