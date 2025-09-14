package com.example.TiendaSuplementos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailVerificationResult {
    private String id;
    private String status;
    
    @JsonProperty("email_address")
    private String emailAddress;
    
    @JsonProperty("email_status")
    private String emailStatus;

    public EmailVerificationResult() {}

    public EmailVerificationResult(String id, String status, String emailAddress, String emailStatus) {
        this.id = id;
        this.status = status;
        this.emailAddress = emailAddress;
        this.emailStatus = emailStatus;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }
}