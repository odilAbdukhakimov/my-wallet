package com.example.mywallet.repository;

import com.example.mywallet.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByCreatedDateAndUserEntity_Id(LocalDate localDate, UUID id);

    List<TransactionEntity> findByCreatedDateBetweenAndUserEntity_Id(LocalDate startTime, LocalDate endDate, UUID id);

    List<TransactionEntity> findByCategory_IdAndUserEntity_Id(UUID id, UUID userId);

    List<TransactionEntity> findByTransactionTypeAndUserEntity_Id(String type, UUID userId);

    List<TransactionEntity> findByUserEntity_Id(UUID userId);

    @Query(value = "SELECT * FROM transaction_entity WHERE type = :type AND user_id = :userId", nativeQuery = true)
    List<TransactionEntity> findTransactionsByTypeAndUserId(@Param("type") String type, @Param("userId") UUID userId);
}
