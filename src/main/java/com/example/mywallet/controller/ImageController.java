package com.example.mywallet.controller;

import com.example.mywallet.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("")
    public String uploadPhoto(MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @SneakyThrows
    @GetMapping("{name}")
    public HttpEntity<byte[]> getPhoto(@PathVariable String name) {
        return imageService.getPicture(name);
    }
//    @SneakyThrows
//    @PutMapping("{name}")
//    public void editUPhoto(@PathVariable String name, HttpServletResponse response) {
//        imageService.getPicture(name, response.getOutputStream());
//    }
}
