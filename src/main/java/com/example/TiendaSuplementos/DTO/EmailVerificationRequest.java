package com.example.TiendaSuplementos.DTO;

public class EmailVerificationRequest {
    private String email;
    private EmailVerificationCallbackRequest callback;

    public EmailVerificationRequest() {}

    public EmailVerificationRequest(String email, EmailVerificationCallbackRequest callback) {
        this.email = email;
        this.callback = callback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailVerificationCallbackRequest getCallback() {
        return callback;
    }

    public void setCallback(EmailVerificationCallbackRequest callback) {
        this.callback = callback;
    }
}