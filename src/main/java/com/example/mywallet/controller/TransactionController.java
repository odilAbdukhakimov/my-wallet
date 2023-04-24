package com.example.mywallet.controller;

import com.example.mywallet.dto.request.TransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.service.TransactionService;
import com.example.mywallet.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("balance")
    public ApiResponse myBalance(@RequestBody TransactionRequestDto requestDto, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.create(requestDto,user);
    }

    @GetMapping("{categoryId}")
    public ApiResponse getTransactionByCategory(@PathVariable UUID categoryId, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionByCategory(categoryId, user.getId());
    }

    @GetMapping("today/{type}")
    public ApiResponse getTransactionOnToday(@PathVariable String type, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionOnToday(type, user.getId());
    }

    @GetMapping("week/{type}")
    public ApiResponse getTransactionWeekly(@PathVariable String type, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionOnWeekly(type, user.getId());
    }

    @GetMapping("month/{type}")
    public ApiResponse getTransactionMonthly(@PathVariable String type, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionOnMonthly(type, user.getId());
    }

    @GetMapping("year/{type}")
    public ApiResponse getTransactionYearly(@PathVariable String type, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionOnYearly(type, user.getId());
    }

    @GetMapping("any/{statDate}/{endDate}/{type}")
    public ApiResponse getTransactionAnyDate(
            @PathVariable LocalDate statDate,
            @PathVariable LocalDate endDate,
            @PathVariable String type,
            HttpServletRequest request
    ) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getTransactionListByAnyDate(statDate, endDate, type, user.getId());
    }

    @GetMapping("list/{type}")
    public ApiResponse getAllTransactionsByType(@PathVariable String type, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return transactionService.getAllTransactionsByType(type, user.getId());
    }
}
