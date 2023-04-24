package com.example.mywallet.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CurrencyExchangeRequestDto {
    private int incomingCurrencyId;
    private double amount;
    private int resultCurrencyId;
}
