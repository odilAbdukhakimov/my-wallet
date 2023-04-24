package com.example.mywallet.controller;

import com.example.mywallet.dto.request.STransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.service.ScheduledTransactionService;
import com.example.mywallet.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/s-transaction/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class ScheduledTransactionController {
    private final ScheduledTransactionService service;
    private final UserService userService;

    @PostMapping("add")
    public ApiResponse createScheduled(@RequestBody STransactionRequestDto requestDto, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return service.create(requestDto, user.getId());
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

    @GetMapping("get")
    public ApiResponse getAllScheduledOfUser(HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return service.getAllScheduledTransactionsOfUser(user.getId());
    }
}
