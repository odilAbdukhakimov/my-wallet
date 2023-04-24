package com.example.mywallet.repository;

import com.example.mywallet.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<BalanceEntity, UUID> {
}
