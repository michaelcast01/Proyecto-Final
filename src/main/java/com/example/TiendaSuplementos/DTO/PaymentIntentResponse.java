package com.example.TiendaSuplementos.DTO;

public class PaymentIntentResponse {
    
    private String clientSecret;
    private String id;
    private String status;
    private Long amount;
    private String currency;
    private String description;
    
    public PaymentIntentResponse() {}
    
    public PaymentIntentResponse(String clientSecret, String id, String status, Long amount, String currency) {
        this.clientSecret = clientSecret;
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
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
}