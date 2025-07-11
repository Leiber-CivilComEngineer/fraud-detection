package com.leiber.frauddetection.transaction_api.service;

import com.leiber.frauddetection.transaction_api.metrics.FraudMetrics;
import com.leiber.frauddetection.transaction_api.model.Transaction;
import com.leiber.frauddetection.transaction_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FraudMetrics fraudMetrics;

    public void processTransaction(Transaction transaction) {
        // Total count + 1
        fraudMetrics.incrementTransactionCount();

        // Save to DB
        transactionRepository.save(transaction);
        System.out.println("✅ Saved transaction to DB: " + transaction.getTransactionId());

        // Check fraud
        if (isFraudulent(transaction)) {
            fraudMetrics.incrementFraudCount();
            System.out.println("🚨 Potential fraud detected!");
        }
    }

    private boolean isFraudulent(Transaction transaction) {
        return transaction.getAmount() > 5000;
    }
}
