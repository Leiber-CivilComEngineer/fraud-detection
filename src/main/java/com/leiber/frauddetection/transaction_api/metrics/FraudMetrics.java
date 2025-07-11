package com.leiber.frauddetection.transaction_api.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class FraudMetrics {

    private final Counter totalTransactionsCounter;
    private final Counter fraudDetectedCounter;

    public FraudMetrics(MeterRegistry meterRegistry) {
        this.totalTransactionsCounter = meterRegistry.counter("fraud_transactions_total");
        this.fraudDetectedCounter = meterRegistry.counter("fraud_detected_total");
    }

    public void incrementTransactionCount() {
        totalTransactionsCounter.increment();
    }

    public void incrementFraudCount() {
        fraudDetectedCounter.increment();
    }
}
