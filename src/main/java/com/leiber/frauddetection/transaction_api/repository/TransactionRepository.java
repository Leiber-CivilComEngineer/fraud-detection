package com.leiber.frauddetection.transaction_api.repository;

import com.leiber.frauddetection.transaction_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}