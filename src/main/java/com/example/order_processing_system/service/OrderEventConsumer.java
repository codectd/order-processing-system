package com.example.order_processing_system.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.order_processing_system.model.Order;
import com.google.gson.Gson;

@Service
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    public OrderEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void consume(String message) {
        Order order = new Gson().fromJson(message, Order.class);
        inventoryService.processOrder(order);
    }
}
