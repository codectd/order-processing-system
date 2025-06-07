package com.example.order_processing_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.order_processing_system.model.InventoryUpdate;
import com.google.gson.Gson;

@Service
public class InventoryEventProducer {

    @Value("${inventory.topic.name:inventory.updated}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public InventoryEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInventoryUpdatedEvent(InventoryUpdate update) {
        String message = new Gson().toJson(update);
        kafkaTemplate.send(topic, message);
    }
}
