package com.example.mywallet.controller;

import com.example.mywallet.dto.request.NoteRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/note/")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("add")
    public ApiResponse addNote(@RequestBody NoteRequestDto requestDto) {
        return noteService.create(requestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateNote(@PathVariable UUID id, @RequestBody NoteRequestDto requestDto) {
        return noteService.update(id, requestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteNote(@PathVariable UUID id) {
        return noteService.delete(id);
    }

    @GetMapping("get/{userId}")
    public ApiResponse getAllNotesOfUser(@PathVariable UUID userId) {
        return noteService.getAllNotesOfUser(userId);
    }
}
