package com.example.mywallet.controller;

import com.example.mywallet.dto.request.STransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.ScheduledTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/s-transaction/")
@RequiredArgsConstructor
public class ScheduledTransactionController {
    private final ScheduledTransactionService service;

    @PostMapping("add")
    public ApiResponse createScheduled(@RequestBody STransactionRequestDto requestDto) {
        return service.create(requestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateScheduled(@PathVariable UUID id,
                                       @RequestBody STransactionRequestDto requestDto) {
        return service.update(id, requestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteScheduled(@PathVariable UUID id) {
        return service.delete(id);
    }

    @GetMapping("get/{userId}")
    public ApiResponse getAllScheduledOfUser(@PathVariable UUID userId) {
        return service.getAllScheduledTransactionsOfUser(userId);
    }
}
