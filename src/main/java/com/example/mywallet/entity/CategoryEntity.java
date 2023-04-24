package com.example.mywallet.entity;

import com.example.mywallet.dto.request.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {
    private String name;
    private String type;
    private String iconUrl;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<CategoryEntity> subcategory;

    public static CategoryEntity of(CategoryRequestDto dto) {
        return CategoryEntity.builder()
                .name(dto.getName())
                .type(dto.getType())
                .build();
    }
}
