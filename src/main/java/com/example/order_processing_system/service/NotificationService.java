package com.example.order_processing_system.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.order_processing_system.model.InventoryUpdate;
import com.example.order_processing_system.model.PaymentStatus;
import com.google.gson.Gson;

@Service
public class NotificationService {

    @KafkaListener(topics = "inventory.updated", groupId = "notification-group")
    public void handleInventoryUpdate(String message) {
        InventoryUpdate update = new Gson().fromJson(message, InventoryUpdate.class);
        sendNotification("Inventory Update", update.getStatus());
    }

    @KafkaListener(topics = "payment.processed", groupId = "notification-group")
    public void handlePaymentStatus(String message) {
        PaymentStatus status = new Gson().fromJson(message, PaymentStatus.class);
        sendNotification("Payment Status", status.getStatus());
    }

    private void sendNotification(String subject, String content) {
        // Implement notification logic (e.g., send email or log to console)
        System.out.println(subject + ": " + content);
    }
}
