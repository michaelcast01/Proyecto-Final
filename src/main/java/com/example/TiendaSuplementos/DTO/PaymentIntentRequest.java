package com.example.TiendaSuplementos.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentIntentRequest {
    
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a 0")
    private Long amount; // Amount in cents (e.g., 1000 = $10.00)
    
    @NotNull(message = "La moneda es requerida")
    private String currency = "usd"; // Default to USD
    
    private String description;
    
    // Customer information (optional)
    private String customerEmail;
    private String customerName;
    
    // Return URL for redirect-based payment methods (optional)
    private String returnUrl;
    
    // Card details for direct payment processing (optional)
    private CardDetails card;
    
    // Constructors
    public PaymentIntentRequest() {}
    
    public PaymentIntentRequest(Long amount, String currency, String description) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getAmount() {
        return amount;
    }
    
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }
    
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    public CardDetails getCard() {
        return card;
    }
    
    public void setCard(CardDetails card) {
        this.card = card;
    }
}