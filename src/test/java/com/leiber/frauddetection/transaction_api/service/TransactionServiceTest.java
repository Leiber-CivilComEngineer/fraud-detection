package com.leiber.frauddetection.transaction_api.service;

import com.leiber.frauddetection.transaction_api.metrics.FraudMetrics;
import com.leiber.frauddetection.transaction_api.model.Transaction;
import com.leiber.frauddetection.transaction_api.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FraudMetrics fraudMetrics;

    @InjectMocks
    private TransactionService transactionService;

    public TransactionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransaction_withValidAmount_savesTransaction() {
        Transaction tx = new Transaction();
        tx.setTransactionId("txn1001");
        tx.setUserId("user001");
        tx.setAmount(500);
        tx.setChannel("web");
        tx.setLocation("Sydney");
        tx.setTimestamp("2025-07-13T00:00:00Z");

        transactionService.processTransaction(tx);

        verify(transactionRepository, times(1)).save(tx);
    }

    @Test
    public void testProcessTransaction_withNegativeAmount_throwsException() {
        Transaction tx = new Transaction();
        tx.setTransactionId("txn1002");
        tx.setUserId("user002");
        tx.setAmount(-100);
        tx.setChannel("app");
        tx.setLocation("Melbourne");
        tx.setTimestamp("2025-07-13T00:00:00Z");

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.processTransaction(tx);
        });

        verify(transactionRepository, never()).save(any());
    }
}
