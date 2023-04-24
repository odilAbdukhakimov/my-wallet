package com.example.mywallet.controller;

import com.example.mywallet.dto.request.CurrencyExchangeRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversion/")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyService currencyService;

    @PostMapping
    public ApiResponse conversion(@RequestBody CurrencyExchangeRequestDto requestDto) {
        return currencyService.conversion(requestDto);
    }
}
