package com.example.order_processing_system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.order_processing_system.model.InventoryItem;
import com.example.order_processing_system.model.InventoryUpdate;
import com.example.order_processing_system.model.Order;
import com.example.order_processing_system.repository.InventoryItemRepository;

@Service
public class InventoryService {

    private final InventoryItemRepository repository;
    private final InventoryEventProducer inventoryEventProducer;

    public InventoryService(InventoryItemRepository repository,
                            InventoryEventProducer inventoryEventProducer) {
        this.repository = repository;
        this.inventoryEventProducer = inventoryEventProducer;
    }

    public void processOrder(Order order) {
        Optional<InventoryItem> itemOpt = repository.findByProduct(order.getProduct());
    
        boolean available = itemOpt
            .filter(item -> item.getAvailableQuantity() >= order.getQuantity())
            .isPresent();
    
        String status = available ? "IN_STOCK" : "OUT_OF_STOCK";
        inventoryEventProducer.sendInventoryUpdatedEvent(new InventoryUpdate(order.getId(), status));
    }
    
}
