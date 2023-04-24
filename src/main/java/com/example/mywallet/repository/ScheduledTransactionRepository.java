package com.example.mywallet.repository;

import com.example.mywallet.entity.ScheduledTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransactionEntity, UUID> {
    List<ScheduledTransactionEntity> findByUserId(UUID id);
    List<ScheduledTransactionEntity> findByPlanDate(LocalDate date);
}
