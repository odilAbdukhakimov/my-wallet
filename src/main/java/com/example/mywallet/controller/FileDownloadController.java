package com.example.mywallet.controller;

import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/api/file/")
@RequiredArgsConstructor
public class FileDownloadController {
    private final FileService fileService;

    @GetMapping("{fileName}")
    public HttpEntity<ByteArrayResource> downloadFile(@PathVariable String fileName)  {
        return fileService.getFile(fileName);
    }
}
