package com.example.mywallet.entity;

import com.example.mywallet.dto.request.TransactionRequestDto;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends BaseEntity {
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
    private String transactionType;
    private LocalDate createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @OneToOne
    private CategoryEntity category;

    public static TransactionEntity of(TransactionRequestDto dto) {
        return TransactionEntity.builder()
                .amount(dto.getAmount())
                .status(TransactionStatusEnum.IN_PROGRESS)
                .createdDate(LocalDate.now())
                .build();


    }
}
