/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

package com.udea.banco2025.repository;
import com.udea.banco2025.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountNumber(String accountNumber);
}
