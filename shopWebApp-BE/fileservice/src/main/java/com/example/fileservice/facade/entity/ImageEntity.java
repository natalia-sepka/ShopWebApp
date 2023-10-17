package com.example.fileservice.facade.entity;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public class ImageEntity {
    private long id;
    private String uuid;
    private String path;
    private boolean isUsed;
    private LocalDate createAt;
}
