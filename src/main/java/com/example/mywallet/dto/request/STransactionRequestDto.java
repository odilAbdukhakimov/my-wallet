package com.example.mywallet.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class STransactionRequestDto {
    private double amount;
    private LocalDate planDate;
//    private UUID userId;
    private UUID categoryId;
}
