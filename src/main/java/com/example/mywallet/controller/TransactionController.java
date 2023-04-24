package com.example.mywallet.controller;

import com.example.mywallet.dto.request.TransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction/")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("balance")
    public ApiResponse myBalance(@RequestBody TransactionRequestDto requestDto) {
        return transactionService.create(requestDto);
    }

    @GetMapping("{userId}/{categoryId}")
    public ApiResponse getTransactionByCategory(@PathVariable UUID userId, @PathVariable UUID categoryId) {
        return transactionService.getTransactionByCategory(categoryId, userId);
    }

    @GetMapping("today/{userId}/{type}")
    public ApiResponse getTransactionOnToday(@PathVariable UUID userId, @PathVariable String type) {
        return transactionService.getTransactionOnToday(type, userId);
    }

    @GetMapping("week/{userId}/{type}")
    public ApiResponse getTransactionWeekly(@PathVariable UUID userId, @PathVariable String type) {
        return transactionService.getTransactionOnWeekly(type, userId);
    }

    @GetMapping("month/{userId}/{type}")
    public ApiResponse getTransactionMonthly(@PathVariable UUID userId, @PathVariable String type) {
        return transactionService.getTransactionOnMonthly(type, userId);
    }

    @GetMapping("year/{userId}/{type}")
    public ApiResponse getTransactionYearly(@PathVariable UUID userId, @PathVariable String type) {
        return transactionService.getTransactionOnYearly(type, userId);
    }

    @GetMapping("any/{userId}/{statDate}/{endDate}/{type}")
    public ApiResponse getTransactionAnyDate(
            @PathVariable UUID userId,
            @PathVariable LocalDate statDate,
            @PathVariable LocalDate endDate,
            @PathVariable String type
    ) {
        return transactionService.getTransactionListByAnyDate(statDate, endDate, type, userId);
    }

    @GetMapping("list/{userId}/{type}")
    public ApiResponse getAllTransactionsByType(@PathVariable UUID userId, @PathVariable String type) {
        return transactionService.getAllTransactionsByType(type, userId);
    }
}
