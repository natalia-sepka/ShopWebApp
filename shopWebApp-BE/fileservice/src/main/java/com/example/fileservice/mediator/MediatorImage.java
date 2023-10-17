package com.example.fileservice.mediator;

import com.example.fileservice.facade.entity.ImageDTO;
import com.example.fileservice.facade.entity.ImageEntity;
import com.example.fileservice.facade.entity.ImageResponse;
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
            imageEntity = imageService.save(imageEntity);
            return ResponseEntity.ok(ImageDTO.builder().uuid(imageEntity.getUuid()).createAt(imageEntity.getCreateAt()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new ImageResponse("Can not save file."));
        }

    }
}
