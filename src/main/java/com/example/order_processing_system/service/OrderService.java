package com.example.order_processing_system.service;

import com.example.order_processing_system.model.Order;
import com.example.order_processing_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        orderEventProducer.sendOrderCreatedEvent(savedOrder);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> updateOrder(Long id, Order updated) {
        return orderRepository.findById(id).map(order -> {
            order.setProduct(updated.getProduct());
            order.setQuantity(updated.getQuantity());
            order.setPrice(updated.getPrice());
            return orderRepository.save(order);
        });
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

