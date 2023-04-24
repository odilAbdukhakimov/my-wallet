package com.example.mywallet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionStatisticDto {
    private List<TransactionResponseDto> transactions;
    @JsonProperty("count_transactions")
    private int countTransactions;
    private double amount;
    private String fileUrl;
}
