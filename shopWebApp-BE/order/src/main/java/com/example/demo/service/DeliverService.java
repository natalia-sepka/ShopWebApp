package com.example.demo.service;

import com.example.demo.entity.DeliverDTO;
import com.example.demo.repository.DeliverRepository;
import com.example.demo.translators.DeliverToDeliverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliverService {

    private final DeliverRepository deliverRepository;
    private final DeliverToDeliverDTO deliverToDeliverDTO;

    public List<DeliverDTO> getAllDeliver() {
        return deliverRepository.findAll().stream().map(deliverToDeliverDTO::deliverDTO).collect(Collectors.toList());
    }
}

