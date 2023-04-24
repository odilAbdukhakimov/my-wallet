package com.example.mywallet.controller;

import com.example.mywallet.dto.request.NoteRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.service.NoteService;
import com.example.mywallet.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/note/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @PostMapping("add")
    public ApiResponse addNote(@RequestBody NoteRequestDto requestDto, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return noteService.create(requestDto, user);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateNote(@PathVariable UUID id, @RequestBody NoteRequestDto requestDto) {
        return noteService.update(id, requestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteNote(@PathVariable UUID id) {
        return noteService.delete(id);
    }

    @GetMapping("get")
    public ApiResponse getAllNotesOfUser(HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return noteService.getAllNotesOfUser(user);
    }
}
