package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.DTO.EmailVerificationRequest;
import com.example.TiendaSuplementos.DTO.EmailVerificationResult;
import com.example.TiendaSuplementos.Model.EmailVerification;
import com.example.TiendaSuplementos.Service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/email-verification")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<?> initiateVerification(@RequestBody EmailVerificationRequest request) {
        try {
            // Validar request
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Email address is required"));
            }

            if (request.getCallback() == null) {
                return ResponseEntity.badRequest().body(createErrorResponse("Callback configuration is required"));
            }

            if (request.getCallback().getUrl() == null || request.getCallback().getUrl().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Callback URL is required"));
            }

            if (request.getCallback().getMethod() == null || request.getCallback().getMethod().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Callback method is required"));
            }

            String verificationId = emailVerificationService.initiateEmailVerification(request);

            Map<String, Object> response = new HashMap<>();
            response.put("verification_id", verificationId);
            response.put("status", "PENDING");
            response.put("message", "Email verification initiated. You will receive a callback when completed.");
            response.put("email", request.getEmail());

            return ResponseEntity.accepted().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error initiating email verification: " + e.getMessage()));
        }
    }


    @GetMapping("/status/{verificationId}")
    public ResponseEntity<?> getVerificationStatus(@PathVariable String verificationId) {
        try {
            Optional<EmailVerification> verificationOpt = emailVerificationService.getVerificationStatus(verificationId);

            if (verificationOpt.isPresent()) {
                EmailVerification verification = verificationOpt.get();
                
                Map<String, Object> response = new HashMap<>();
                response.put("verification_id", verification.getVerificationId());
                response.put("email_address", verification.getEmailAddress());
                response.put("status", verification.getStatus());
                response.put("email_status", verification.getEmailStatus());
                response.put("created_at", verification.getCreatedAt());
                response.put("updated_at", verification.getUpdatedAt());
                response.put("verified_at", verification.getVerifiedAt());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving verification status: " + e.getMessage()));
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<?> receiveCallback(@RequestBody EmailVerificationResult result) {
        try {
            emailVerificationService.receiveCallback(result);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Callback received successfully");
            response.put("verification_id", result.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error processing callback: " + e.getMessage()));
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getVerificationInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Email Verification API");
        info.put("version", "1.0.0");
        info.put("description", "Asynchronous email verification service with callback support");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("POST /api/email-verification/verify", "Initiate email verification");
        endpoints.put("GET /api/email-verification/status/{id}", "Get verification status");
        endpoints.put("POST /api/email-verification/callback", "Example callback endpoint");
        endpoints.put("GET /api/email-verification/info", "Get API information");
        
        info.put("endpoints", endpoints);
        
        Map<String, String> emailStatuses = new HashMap<>();
        emailStatuses.put("VALID", "The email address is valid and can be delivered");
        emailStatuses.put("INVALID", "The email address is invalid and cannot be delivered");
        emailStatuses.put("UNKNOWN", "We are unsure and the address could or could not be deliverable");
        
        info.put("email_statuses", emailStatuses);
        
        Map<String, String> verificationStatuses = new HashMap<>();
        verificationStatuses.put("PENDING", "Verification is in progress");
        verificationStatuses.put("COMPLETED", "Verification completed successfully");
        verificationStatuses.put("FAILED", "Verification failed due to an error");
        
        info.put("verification_statuses", verificationStatuses);

        return ResponseEntity.ok(info);
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", true);
        error.put("message", message);
        error.put("timestamp", System.currentTimeMillis());
        return error;
    }
}