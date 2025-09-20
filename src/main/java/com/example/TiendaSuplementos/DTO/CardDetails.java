package com.example.TiendaSuplementos.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardDetails {
    
    @NotNull(message = "El número de tarjeta es requerido")
    @Pattern(regexp = "^[0-9]{13,19}$", message = "Número de tarjeta inválido")
    private String number;
    
    @NotNull(message = "El mes de expiración es requerido")
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Mes debe ser entre 01 y 12")
    private String expMonth;
    
    @NotNull(message = "El año de expiración es requerido")
    @Pattern(regexp = "^[0-9]{4}$", message = "Año debe tener 4 dígitos")
    private String expYear;
    
    @NotNull(message = "El CVC es requerido")
    @Size(min = 3, max = 4, message = "CVC debe tener 3 o 4 dígitos")
    private String cvc;
    
    private String postalCode;
    private String country = "US"; // Default to US
    
    public CardDetails() {}
    
    public CardDetails(String number, String expMonth, String expYear, String cvc) {
        this.number = number;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = cvc;
    }
    
    // Getters and Setters
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getExpMonth() {
        return expMonth;
    }
    
    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }
    
    public String getExpYear() {
        return expYear;
    }
    
    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }
    
    public String getCvc() {
        return cvc;
    }
    
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}