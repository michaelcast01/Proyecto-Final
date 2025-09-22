package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.DTO.PaymentConfirmationRequest;
import com.example.TiendaSuplementos.DTO.PaymentConfirmationWithCardRequest;
import com.example.TiendaSuplementos.DTO.PaymentIntentRequest;
import com.example.TiendaSuplementos.DTO.PaymentIntentResponse;
import com.example.TiendaSuplementos.DTO.TestPaymentRequest;
import com.example.TiendaSuplementos.Service.StripePaymentService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "APIs para gestión de pagos con Stripe")
public class StripePaymentController {

    @Autowired
    private StripePaymentService stripePaymentService;

    @PostMapping("/create-payment-intent")
    @Operation(summary = "Crear un PaymentIntent", 
               description = "Crea un PaymentIntent en Stripe para procesar un pago. Envía el monto total a pagar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PaymentIntent creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor o de Stripe")
    })
    public ResponseEntity<?> createPaymentIntent(
            @Valid @RequestBody PaymentIntentRequest request) {
        
        try {
            PaymentIntentResponse response = stripePaymentService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
            
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el PaymentIntent");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/payment-intent/{paymentIntentId}")
    @Operation(summary = "Obtener un PaymentIntent", 
               description = "Recupera los detalles de un PaymentIntent existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PaymentIntent recuperado exitosamente"),
        @ApiResponse(responseCode = "404", description = "PaymentIntent no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor o de Stripe")
    })
    public ResponseEntity<?> getPaymentIntent(
            @Parameter(description = "ID del PaymentIntent", required = true)
            @PathVariable String paymentIntentId) {
        
        try {
            PaymentIntentResponse response = stripePaymentService.getPaymentIntent(paymentIntentId);
            return ResponseEntity.ok(response);
            
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al recuperar el PaymentIntent");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/confirm-payment")
    @Operation(summary = "Confirmar un pago", 
               description = "Confirma un PaymentIntent en el servidor (útil para confirmación del lado del servidor)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago confirmado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al confirmar el pago"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor o de Stripe")
    })
    public ResponseEntity<?> confirmPayment(
            @Valid @RequestBody PaymentConfirmationRequest request) {
        
        try {
            PaymentIntentResponse response = stripePaymentService.confirmPaymentIntent(request.getPaymentIntentId());
            return ResponseEntity.ok(response);
            
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al confirmar el pago");
            error.put("message", e.getMessage());
            error.put("advice", "Este PaymentIntent necesita un método de pago. Usa create-payment-intent con datos de tarjeta o confirm-with-card.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/confirm-with-card")
    @Operation(summary = "Confirmar pago con tarjeta", 
               description = "Agrega método de pago y confirma un PaymentIntent existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago confirmado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al procesar el pago"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> confirmPaymentWithCard(
            @Valid @RequestBody PaymentConfirmationWithCardRequest request) {
        
        try {
            PaymentIntentResponse response = stripePaymentService.confirmPaymentWithCard(
                request.getPaymentIntentId(), 
                request.getCard()
            );
            return ResponseEntity.ok(response);
            
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al procesar el pago");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/create-test-payment")
    @Operation(summary = "Crear pago de prueba con token", 
               description = "Crea y procesa un pago usando tokens de prueba seguros de Stripe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al procesar el pago"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> createTestPayment(
            @Valid @RequestBody TestPaymentRequest request) {
        
        try {
            PaymentIntentResponse response = stripePaymentService.createTestPayment(request);
            return ResponseEntity.ok(response);
            
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al procesar el pago de prueba");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio de pagos esté funcionando")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Stripe Payment Service");
        response.put("timestamp", java.time.Instant.now().toString());
        return ResponseEntity.ok(response);
    }
}