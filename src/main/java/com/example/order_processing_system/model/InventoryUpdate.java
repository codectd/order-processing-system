package com.example.order_processing_system.model;

public class InventoryUpdate {
    private Long orderId;
    private String status;

    public InventoryUpdate() {}

    public InventoryUpdate(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
