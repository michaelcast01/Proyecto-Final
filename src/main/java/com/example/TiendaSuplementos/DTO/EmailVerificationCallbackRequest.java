package com.example.TiendaSuplementos.DTO;

import java.util.Map;

public class EmailVerificationCallbackRequest {
    private String url;
    private String method;
    private Map<String, String> headers;

    public EmailVerificationCallbackRequest() {}

    public EmailVerificationCallbackRequest(String url, String method, Map<String, String> headers) {
        this.url = url;
        this.method = method;
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}