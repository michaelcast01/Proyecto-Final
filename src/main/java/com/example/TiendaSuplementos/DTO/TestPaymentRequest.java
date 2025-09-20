package com.example.TiendaSuplementos.DTO;

public class TestPaymentRequest {
    
    private Long amount;
    private String currency = "usd";
    private String description;
    private String customerEmail;
    private String customerName;
    
    private String testToken = "tok_visa";
    
    // Constructors
    public TestPaymentRequest() {}
    
    public TestPaymentRequest(Long amount, String currency, String description) {
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
    
    public String getTestToken() {
        return testToken;
    }
    
    public void setTestToken(String testToken) {
        this.testToken = testToken;
    }
}