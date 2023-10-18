package com.example.fileservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Getter
@Setter
public class ImageEntity {
    private long id;
    private String uuid;
    private String path;
    private boolean isUsed;
    private LocalDate createAt;
}
