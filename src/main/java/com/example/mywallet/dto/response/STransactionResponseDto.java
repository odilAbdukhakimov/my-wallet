package com.example.mywallet.dto.response;

import com.example.mywallet.entity.ScheduledTransactionEntity;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class STransactionResponseDto {
    private double amount;
    private TransactionStatusEnum status;
    private LocalDate createdDate;
    private LocalDate planDate;
    private String userEntityName;
    private String categoryName;

    public static STransactionResponseDto from(ScheduledTransactionEntity entity){
        return STransactionResponseDto.builder()
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .planDate(entity.getPlanDate())
                .build();
    }
}
