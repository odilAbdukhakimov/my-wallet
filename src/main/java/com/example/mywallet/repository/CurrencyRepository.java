package com.example.mywallet.repository;

import com.example.mywallet.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Integer> {
}
