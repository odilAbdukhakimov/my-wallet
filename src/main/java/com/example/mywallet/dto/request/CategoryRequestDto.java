package com.example.mywallet.dto.request;

import lombok.Data;

import java.util.UUID;
@Data
public class CategoryRequestDto {
    private String name;
    private String type;
    private UUID topCategoryId;
}
