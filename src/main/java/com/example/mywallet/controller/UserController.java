package com.example.mywallet.controller;

import com.example.mywallet.dto.request.UserUpdateRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping("{id}")
//    public ApiResponse getById(@PathVariable UUID id, HttpServletResponse response) throws IOException {
//        return userService.getById(id);
//    }

    @PostMapping(value = "upload-img/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse uploadPhoto(@PathVariable UUID id, @RequestPart("file") MultipartFile file) {
        return userService.uploadPhoto(id, file);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequestDto dto) {
        return userService.edit(id, dto);
    }
}
