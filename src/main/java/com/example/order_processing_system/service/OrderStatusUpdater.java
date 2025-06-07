package com.example.order_processing_system.service;

import com.example.order_processing_system.model.OrderStatus;
import com.example.order_processing_system.model.InventoryUpdate;
import com.example.order_processing_system.model.PaymentStatus;
import com.example.order_processing_system.repository.OrderRepository;
import com.google.gson.Gson;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusUpdater {

    private final OrderRepository orderRepository;

    public OrderStatusUpdater(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "inventory.updated", groupId = "order-status-group")
    public void handleInventoryUpdate(String message) {
        InventoryUpdate update = new Gson().fromJson(message, InventoryUpdate.class);
        orderRepository.findById(update.getOrderId()).ifPresent(order -> {
            OrderStatus status = update.getStatus().equalsIgnoreCase("IN_STOCK")
                ? OrderStatus.IN_STOCK
                : OrderStatus.OUT_OF_STOCK;
            order.setStatus(status);
            orderRepository.save(order);
        });
    }

    @KafkaListener(topics = "payment.processed", groupId = "order-status-group")
    public void handlePaymentUpdate(String message) {
        PaymentStatus status = new Gson().fromJson(message, PaymentStatus.class);
        orderRepository.findById(status.getOrderId()).ifPresent(order -> {
            OrderStatus newStatus = status.getStatus().equalsIgnoreCase("SUCCESS")
                ? OrderStatus.PAID
                : OrderStatus.PAYMENT_FAILED;
            order.setStatus(newStatus);
            orderRepository.save(order);
        });
    }
}

