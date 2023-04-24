package com.example.mywallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    private double amount;
//    private UUID userId;
    private UUID categoryId;
}
