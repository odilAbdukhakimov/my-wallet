package com.example.mywallet.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyExchangeResponseDto {
    private String incomingCurrencyCcy;
    private double enterVal;
    private String resultCurrencyCcy;
    private double exitVal;
}
