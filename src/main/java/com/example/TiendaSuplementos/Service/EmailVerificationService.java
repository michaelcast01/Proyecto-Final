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

    // Regex pattern for basic email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    /**
     * Inicia el proceso de verificación de email de forma asíncrona
     */
    public String initiateEmailVerification(EmailVerificationRequest request) {
        // Generar ID único para la verificación
        String verificationId = UUID.randomUUID().toString();

        try {
            // Convertir headers a JSON string
            String headersJson = objectMapper.writeValueAsString(request.getCallback().getHeaders());

            // Crear entidad de verificación
            EmailVerification verification = new EmailVerification(
                verificationId,
                request.getEmail(),
                "PENDING",
                request.getCallback().getUrl(),
                request.getCallback().getMethod(),
                headersJson
            );

            // Guardar en base de datos
            repository.save(verification);

            // Iniciar verificación asíncrona
            processEmailVerificationAsync(verificationId);

            return verificationId;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing callback headers", e);
        }
    }

    /**
     * Procesa la verificación de email de forma asíncrona
     */
    @Async("emailVerificationTaskExecutor")
    public CompletableFuture<Void> processEmailVerificationAsync(String verificationId) {
        try {
            // Simular tiempo de procesamiento (en una implementación real, aquí iría la lógica de verificación)
            Thread.sleep(2000); // 2 segundos de simulación

            Optional<EmailVerification> verificationOpt = repository.findByVerificationId(verificationId);
            
            if (verificationOpt.isPresent()) {
                EmailVerification verification = verificationOpt.get();
                
                // Simular verificación de email
                String emailStatus = performEmailVerification(verification.getEmailAddress());
                
                // Actualizar estado
                verification.setStatus("COMPLETED");
                verification.setEmailStatus(emailStatus);
                verification.setVerifiedAt(LocalDateTime.now());
                repository.save(verification);

                // Enviar callback
                sendCallback(verification);
            }

        } catch (Exception e) {
            // En caso de error, actualizar estado
            repository.findByVerificationId(verificationId).ifPresent(verification -> {
                verification.setStatus("FAILED");
                repository.save(verification);
            });
        }

        return CompletableFuture.completedFuture(null);
    }

    /**
     * Simula la verificación de email (en implementación real, aquí iría la lógica compleja)
     */
    private String performEmailVerification(String email) {
        // Validación básica de formato
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "INVALID";
        }

        // Simular diferentes resultados basados en el dominio
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

    /**
     * Envía el callback con el resultado de la verificación
     */
    private void sendCallback(EmailVerification verification) {
        try {
            // Crear resultado para el callback
            EmailVerificationResult result = new EmailVerificationResult(
                verification.getVerificationId(),
                verification.getStatus(),
                verification.getEmailAddress(),
                verification.getEmailStatus()
            );

            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Agregar headers personalizados del callback
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

            // Crear request entity
            HttpEntity<EmailVerificationResult> requestEntity = new HttpEntity<>(result, headers);

            // Determinar método HTTP
            HttpMethod method = HttpMethod.valueOf(verification.getCallbackMethod().toUpperCase());

            // Enviar callback
            ResponseEntity<String> response = restTemplate.exchange(
                verification.getCallbackUrl(),
                method,
                requestEntity,
                String.class
            );

            // Log resultado (en implementación real, podrías almacenar esto)
            System.out.println("Callback sent successfully. Response: " + response.getStatusCode());

        } catch (Exception e) {
            // Log error pero no fallar todo el proceso
            System.err.println("Error sending callback for verification " + 
                verification.getVerificationId() + ": " + e.getMessage());
        }
    }

    /**
     * Obtiene el estado de una verificación por ID
     */
    public Optional<EmailVerification> getVerificationStatus(String verificationId) {
        return repository.findByVerificationId(verificationId);
    }

    /**
     * Endpoint para recibir callbacks (útil para testing)
     */
    public void receiveCallback(EmailVerificationResult result) {
        System.out.println("Callback received for verification: " + result.getId());
        System.out.println("Email: " + result.getEmailAddress());
        System.out.println("Status: " + result.getStatus());
        System.out.println("Email Status: " + result.getEmailStatus());
    }
}