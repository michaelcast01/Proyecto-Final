package com.example.TiendaSuplementos.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PaymentConfirmationWithCardRequest {
    
    @NotNull(message = "El PaymentIntent ID es requerido")
    private String paymentIntentId;
    
    @NotNull(message = "Los datos de la tarjeta son requeridos")
    @Valid
    private CardDetails card;
    
    // Constructors
    public PaymentConfirmationWithCardRequest() {}
    
    public PaymentConfirmationWithCardRequest(String paymentIntentId, CardDetails card) {
        this.paymentIntentId = paymentIntentId;
        this.card = card;
    }
    
    // Getters and Setters
    public String getPaymentIntentId() {
        return paymentIntentId;
    }
    
    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }
    
    public CardDetails getCard() {
        return card;
    }
    
    public void setCard(CardDetails card) {
        this.card = card;
    }
}