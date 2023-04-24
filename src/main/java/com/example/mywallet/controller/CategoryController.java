package com.example.mywallet.controller;

import com.example.mywallet.dto.request.CategoryRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/category/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("add")
    public ApiResponse addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.add(categoryRequestDto);
    }

    @PostMapping(value = "upload-img/{id}", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ApiResponse uploadPhoto(@PathVariable UUID id, @RequestPart("file") MultipartFile file) {
        return categoryService.uploadImage(id, file);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateCategory(
            @PathVariable UUID id,
            @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteCategory(@PathVariable UUID id) {
        return categoryService.delete(id);
    }

    @GetMapping("get-main")
    public ApiResponse getAllTopCategory() {
        return categoryService.topCategoryList();
    }

    @GetMapping("get-all")
    public ApiResponse getAllCategory() {
        return categoryService.getAllCategories();
    }
}
