package com.example.order_processing_system.service;

import org.springframework.stereotype.Service;

import com.example.order_processing_system.model.Payment;
import com.example.order_processing_system.model.PaymentStatus;
import com.google.gson.Gson;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Service
public class PaymentService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.created", groupId = "payment-group")
    public void processPayment(String message) {
        // Simulate payment processing
        Payment payment = new Gson().fromJson(message, Payment.class);
        boolean success = process(payment);
        String status = success ? "SUCCESS" : "FAILED";
        kafkaTemplate.send("payment.processed", new Gson().toJson(new PaymentStatus(payment.getOrderId(), status)));
    }
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
    private boolean process(Payment payment) {
        // Simulate payment processing logic
        return true;
    }
}
