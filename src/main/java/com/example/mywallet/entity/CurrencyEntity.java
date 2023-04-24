package com.example.mywallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity {
    @Id
    private int id;
    @JsonProperty("Ccy")
    private String ccy;
    @JsonProperty("CcyNm_UZ")
    private String ccyNmUz;
    @JsonProperty("Rate")
    private double rate;
}
