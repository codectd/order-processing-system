package com.example.order_processing_system.model;


public class PaymentStatus {
    private Long orderId;
    private String status;

    // Constructor
    public PaymentStatus(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    // Getters and Setters
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
