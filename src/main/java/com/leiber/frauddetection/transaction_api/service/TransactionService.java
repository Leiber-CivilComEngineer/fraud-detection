package com.leiber.frauddetection.transaction_api.service;

import com.leiber.frauddetection.transaction_api.metrics.FraudMetrics;
import com.leiber.frauddetection.transaction_api.model.Transaction;
import com.leiber.frauddetection.transaction_api.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FraudMetrics fraudMetrics;

    public void processTransaction(Transaction transaction) {
        // Increase total count
        fraudMetrics.incrementTransactionCount();

        // Save to DB
        transactionRepository.save(transaction);
        log.info("✅ Saved transaction to DB: ID={}", transaction.getTransactionId());

        // Fraud check
        if (isFraudulent(transaction)) {
            fraudMetrics.incrementFraudCount();
            log.warn("🚨 Fraudulent transaction detected: ID={}, amount={}, user={}",
                    transaction.getTransactionId(),
                    transaction.getAmount(),
                    transaction.getUserId());
        }
    }

    private boolean isFraudulent(Transaction transaction) {
        return transaction.getAmount() > 5000;
    }
}
