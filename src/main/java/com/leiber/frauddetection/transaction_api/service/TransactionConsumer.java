package com.leiber.frauddetection.transaction_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leiber.frauddetection.transaction_api.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @Autowired
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "transactions", groupId = "fraud-detector-group")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);
            transactionService.processTransaction(transaction);
        } catch (Exception e) {
            System.err.println("❌ Error processing transaction: " + e.getMessage());
        }
    }
}
