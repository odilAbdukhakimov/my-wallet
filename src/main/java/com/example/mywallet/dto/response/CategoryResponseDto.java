package com.example.mywallet.dto.response;

import com.example.mywallet.entity.CategoryEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CategoryResponseDto {
    private UUID id;
    private String name;
    private String type;
    private String iconUrl;
    private List<CategoryEntity> subcategory;

    public static CategoryResponseDto from(CategoryEntity entity) {
        return CategoryResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .iconUrl(entity.getIconUrl())
                .subcategory(entity.getSubcategory())
                .build();
    }
}
