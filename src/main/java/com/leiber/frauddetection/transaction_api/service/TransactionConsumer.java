package com.leiber.frauddetection.transaction_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leiber.frauddetection.transaction_api.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.error("❌ Error processing transaction: {}", e.getMessage(), e);
        }
    }
}
