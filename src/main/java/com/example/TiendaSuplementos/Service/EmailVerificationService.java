package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.DTO.EmailVerificationRequest;
import com.example.TiendaSuplementos.DTO.EmailVerificationResult;
import com.example.TiendaSuplementos.Model.EmailVerification;
import com.example.TiendaSuplementos.Repository.EmailVerificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public String initiateEmailVerification(EmailVerificationRequest request) {
        String verificationId = UUID.randomUUID().toString();

        try {
            String headersJson = objectMapper.writeValueAsString(request.getCallback().getHeaders());

            EmailVerification verification = new EmailVerification(
                verificationId,
                request.getEmail(),
                "PENDING",
                request.getCallback().getUrl(),
                request.getCallback().getMethod(),
                headersJson
            );

            repository.save(verification);

            processEmailVerificationAsync(verificationId);

            return verificationId;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing callback headers", e);
        }
    }

    @Async("emailVerificationTaskExecutor")
    public CompletableFuture<Void> processEmailVerificationAsync(String verificationId) {
        try {
            Thread.sleep(2000); // 2 segundos de simulación

            Optional<EmailVerification> verificationOpt = repository.findByVerificationId(verificationId);
            
            if (verificationOpt.isPresent()) {
                EmailVerification verification = verificationOpt.get();
                
                String emailStatus = performEmailVerification(verification.getEmailAddress());
                
                verification.setStatus("COMPLETED");
                verification.setEmailStatus(emailStatus);
                verification.setVerifiedAt(LocalDateTime.now());
                repository.save(verification);

                sendCallback(verification);
            }

        } catch (Exception e) {
            repository.findByVerificationId(verificationId).ifPresent(verification -> {
                verification.setStatus("FAILED");
                repository.save(verification);
            });
        }

        return CompletableFuture.completedFuture(null);
    }

    private String performEmailVerification(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "INVALID";
        }

        String domain = email.substring(email.indexOf("@") + 1);
        
        switch (domain.toLowerCase()) {
            case "gmail.com":
            case "outlook.com":
            case "yahoo.com":
            case "hotmail.com":
                return "VALID";
            case "fake.com":
            case "invalid.com":
                return "INVALID";
            default:
                return "UNKNOWN";
        }
    }

    private void sendCallback(EmailVerification verification) {
        try {
            EmailVerificationResult result = new EmailVerificationResult(
                verification.getVerificationId(),
                verification.getStatus(),
                verification.getEmailAddress(),
                verification.getEmailStatus()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (verification.getCallbackHeaders() != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, String> customHeaders = objectMapper.readValue(
                        verification.getCallbackHeaders(), Map.class);
                    
                    customHeaders.forEach(headers::add);
                } catch (JsonProcessingException e) {
                    // Log error pero continuar con el callback
                    System.err.println("Error parsing callback headers: " + e.getMessage());
                }
            }

            HttpEntity<EmailVerificationResult> requestEntity = new HttpEntity<>(result, headers);

            HttpMethod method = HttpMethod.valueOf(verification.getCallbackMethod().toUpperCase());

            ResponseEntity<String> response = restTemplate.exchange(
                verification.getCallbackUrl(),
                method,
                requestEntity,
                String.class
            );

            System.out.println("Callback sent successfully. Response: " + response.getStatusCode());

        } catch (Exception e) {
            System.err.println("Error sending callback for verification " +
                verification.getVerificationId() + ": " + e.getMessage());
        }
    }

    public Optional<EmailVerification> getVerificationStatus(String verificationId) {
        return repository.findByVerificationId(verificationId);
    }

    public void receiveCallback(EmailVerificationResult result) {
        System.out.println("Callback received for verification: " + result.getId());
        System.out.println("Email: " + result.getEmailAddress());
        System.out.println("Status: " + result.getStatus());
        System.out.println("Email Status: " + result.getEmailStatus());
    }
}