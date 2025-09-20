package com.example.TiendaSuplementos.DTO;

public class PaymentConfirmationRequest {
    
    private String paymentIntentId;
    
    // Constructors
    public PaymentConfirmationRequest() {}
    
    public PaymentConfirmationRequest(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }
    
    // Getters and Setters
    public String getPaymentIntentId() {
        return paymentIntentId;
    }
    
    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }
}