package com.example.order_processing_system.service;

import com.example.order_processing_system.model.Order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


@Service
public class OrderEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${order.topic.name:order.created}")
    private String topicName;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(Order order) {
        String message = new Gson().toJson(order);
        kafkaTemplate.send(topicName, message);
    }
}
