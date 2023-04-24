package com.example.mywallet.entity;

import com.example.mywallet.dto.request.STransactionRequestDto;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTransactionEntity extends BaseEntity {
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
    private LocalDate createdDate;
    private LocalDate planDate;
    private UUID userId;
    private UUID categoryId;

    public static ScheduledTransactionEntity of(STransactionRequestDto dto) {
        return ScheduledTransactionEntity.builder()
                .amount(dto.getAmount())
                .status(TransactionStatusEnum.IN_PROGRESS)
                .createdDate(LocalDate.now())
                .planDate(dto.getPlanDate())
                .userId(dto.getUserId())
                .categoryId(dto.getCategoryId())
                .build();
    }
}
