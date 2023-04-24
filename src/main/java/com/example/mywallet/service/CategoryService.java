package com.example.mywallet.service;

import com.example.mywallet.dto.request.CategoryRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.CategoryResponseDto;
import com.example.mywallet.entity.CategoryEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public ApiResponse add(CategoryRequestDto categoryRequestDto) {
        CategoryEntity of = CategoryEntity.of(categoryRequestDto);
        if (categoryRequestDto.getTopCategoryId() != null && !categoryRequestDto.getTopCategoryId().equals("")) {
            CategoryEntity byId = findById(categoryRequestDto.getTopCategoryId());
            List<CategoryEntity> subcategory = byId.getSubcategory();
            if (subcategory == null)
                subcategory = List.of(of);
            else subcategory.add(of);
            byId.setSubcategory(subcategory);
            categoryRepository.save(byId);
        }
        CategoryEntity save = categoryRepository.save(of);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }

    public ApiResponse uploadImage(UUID categoryId, MultipartFile file) {
        CategoryEntity byId = findById(categoryId);
        String uploaded = imageService.uploadImage(file);
        byId.setIconUrl(uploaded);
        categoryRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "http://localhost:8080/" + uploaded
        );
    }

    public ApiResponse update(UUID id, CategoryRequestDto dto) {
        CategoryEntity byId = findById(id);
        if (dto.getName() != null && !dto.getName().equals("")) {
            byId.setName(dto.getName());
        }
        if (dto.getType() != null) {
            byId.setType(dto.getType());
        }
        CategoryEntity save = categoryRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }

    public ApiResponse delete(UUID id) {
        CategoryEntity byId = findById(id);
        categoryRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    public ApiResponse topCategoryList() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryEntity> topCategory = all.stream().filter((c) ->
                getTopCategoryById(c.getId()) == null).toList();

        List<CategoryResponseDto> responseTopCategory = topCategory.stream().map(CategoryResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                responseTopCategory
        );
    }

    public ApiResponse getAllCategories() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryResponseDto> responseDtoList = all.stream().map(CategoryResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                responseDtoList
        );
    }

    private CategoryEntity findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Category not found"));
    }

    private CategoryEntity getTopCategoryById(UUID id) {
        for (CategoryEntity category : categoryRepository.findAll()) {
            for (CategoryEntity categoryEntity : category.getSubcategory()) {
                if (categoryEntity.getId().equals(id))
                    return categoryEntity;
            }

        }
        return null;

    }

}
