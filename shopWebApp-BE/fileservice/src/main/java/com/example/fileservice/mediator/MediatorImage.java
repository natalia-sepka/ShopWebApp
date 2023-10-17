package com.example.fileservice.mediator;

import com.example.fileservice.facade.entity.ImageEntity;
import com.example.fileservice.service.FtpService;
import com.example.fileservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
public class MediatorImage {
    private final FtpService ftpService;
    private final ImageService imageService;

    private ResponseEntity<?> saveImage(MultipartFile file) {
        try {
            ImageEntity imageEntity = ftpService.uploadFileToFtp(file);
            imageService.save(imageEntity);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Can not save file");
        }

    }
}
