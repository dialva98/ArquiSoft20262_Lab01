/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

package com.udea.banco2025.repository;
import java.util.List;
import com.udea.banco2025.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber, String receiverAccountNumber);
}
