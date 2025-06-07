package com.example.order_processing_system.model;

public class Payment {
    private Long orderId;
    private double amount;
    private String method;

    // Default constructor
    public Payment() {}

    // Parameterized constructor
    public Payment(Long orderId, double amount, String method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
