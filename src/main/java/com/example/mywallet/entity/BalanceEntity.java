package com.example.mywallet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEntity extends BaseEntity{
    @OneToOne
    private CurrencyEntity currency;
    private double balance;
}
