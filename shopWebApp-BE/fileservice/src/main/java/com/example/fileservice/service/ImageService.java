package com.example.fileservice.service;

import com.example.fileservice.facade.entity.ImageEntity;
import com.example.fileservice.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageEntity save(ImageEntity imageEntity) {
        return imageRepository.saveAndFlush(imageEntity);
    }
}
