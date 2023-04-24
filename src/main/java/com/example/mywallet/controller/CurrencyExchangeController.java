package com.example.mywallet.controller;

import com.example.mywallet.dto.request.CurrencyExchangeRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.service.CurrencyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyService currencyService;

    @PostMapping("conversion")
    public ApiResponse conversion(@RequestBody CurrencyExchangeRequestDto requestDto) {
        return currencyService.conversion(requestDto);
    }

    @GetMapping("get")
    public ApiResponse getAllCurrency() {
        return currencyService.getAllCurrencies();
    }
}
