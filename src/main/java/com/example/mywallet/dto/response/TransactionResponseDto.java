package com.example.mywallet.dto.response;

import com.example.mywallet.entity.CategoryEntity;
import com.example.mywallet.entity.TransactionEntity;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TransactionResponseDto {
    private UUID id;
    private double amount;
    private TransactionStatusEnum status;
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty("created_date")
    private LocalDate createdDate;
    @JsonProperty("user_entity")
    private String name;
    private CategoryEntity category;

    public static TransactionResponseDto from(TransactionEntity entity) {
        return TransactionResponseDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .transactionType(entity.getTransactionType())
                .createdDate(entity.getCreatedDate())
                .name(entity.getUserEntity().getName())
                .category(entity.getCategory())
                .build();
    }
}
