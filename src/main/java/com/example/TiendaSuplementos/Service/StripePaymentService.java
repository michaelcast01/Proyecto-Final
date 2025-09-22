package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.DTO.PaymentIntentRequest;
import com.example.TiendaSuplementos.DTO.PaymentIntentResponse;
import com.example.TiendaSuplementos.DTO.TestPaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentService {

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) throws StripeException {
        
        // Build payment intent creation parameters
        PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setDescription(request.getDescription())
                // Enable automatic payment methods but disable redirects
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                        .build()
                );

        Map<String, String> metadata = new HashMap<>();
        if (request.getCustomerEmail() != null) {
            metadata.put("customer_email", request.getCustomerEmail());
        }
        if (request.getCustomerName() != null) {
            metadata.put("customer_name", request.getCustomerName());
        }
        
        if (!metadata.isEmpty()) {
            paramsBuilder.putAllMetadata(metadata);
        }

        PaymentIntent intent = PaymentIntent.create(paramsBuilder.build());

        if (request.getCard() != null) {
            try {
                PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .setCard(
                        PaymentMethodCreateParams.CardDetails.builder()
                            .setNumber(request.getCard().getNumber())
                            .setExpMonth(Long.parseLong(request.getCard().getExpMonth()))
                            .setExpYear(Long.parseLong(request.getCard().getExpYear()))
                            .setCvc(request.getCard().getCvc())
                            .build()
                    )
                    .build();

                PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

                PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                    .setPaymentMethod(paymentMethod.getId())
                    .build();

                intent = intent.confirm(confirmParams);
                
            } catch (Exception e) {
                System.err.println("Error processing card: " + e.getMessage());
            }
        }

        PaymentIntentResponse response = new PaymentIntentResponse(
            intent.getClientSecret(),
            intent.getId(),
            intent.getStatus(),
            intent.getAmount(),
            intent.getCurrency()
        );
        response.setDescription(intent.getDescription());
        
        return response;
    }

    public PaymentIntentResponse getPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        
        PaymentIntentResponse response = new PaymentIntentResponse(
            intent.getClientSecret(),
            intent.getId(),
            intent.getStatus(),
            intent.getAmount(),
            intent.getCurrency()
        );
        response.setDescription(intent.getDescription());
        
        return response;
    }

    public PaymentIntentResponse confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        
        intent = intent.confirm();
        
        PaymentIntentResponse response = new PaymentIntentResponse(
            intent.getClientSecret(),
            intent.getId(),
            intent.getStatus(),
            intent.getAmount(),
            intent.getCurrency()
        );
        response.setDescription(intent.getDescription());
        
        return response;
    }

    public PaymentIntentResponse confirmPaymentWithCard(String paymentIntentId, com.example.TiendaSuplementos.DTO.CardDetails cardDetails) throws StripeException {
        
        // Create payment method with card details
        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
            .setType(PaymentMethodCreateParams.Type.CARD)
            .setCard(
                PaymentMethodCreateParams.CardDetails.builder()
                    .setNumber(cardDetails.getNumber())
                    .setExpMonth(Long.parseLong(cardDetails.getExpMonth()))
                    .setExpYear(Long.parseLong(cardDetails.getExpYear()))
                    .setCvc(cardDetails.getCvc())
                    .build()
            )
            .build();

        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

        PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
            .setPaymentMethod(paymentMethod.getId())
            .build();

        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        intent = intent.confirm(confirmParams);
        
        PaymentIntentResponse response = new PaymentIntentResponse(
            intent.getClientSecret(),
            intent.getId(),
            intent.getStatus(),
            intent.getAmount(),
            intent.getCurrency()
        );
        response.setDescription(intent.getDescription());
        
        return response;
    }

    public PaymentIntentResponse createTestPayment(TestPaymentRequest request) throws StripeException {
        
        // Create payment method using test token (safe for testing)
        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
            .setType(PaymentMethodCreateParams.Type.CARD)
            .setCard(
                PaymentMethodCreateParams.Token.builder()
                    .setToken(request.getTestToken()) // Use test token instead of raw card data
                    .build()
            )
            .build();

        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

        PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setDescription(request.getDescription())
                .setPaymentMethod(paymentMethod.getId())
                .setConfirm(true) // Auto-confirm the payment
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                        .build()
                );

        Map<String, String> metadata = new HashMap<>();
        if (request.getCustomerEmail() != null) {
            metadata.put("customer_email", request.getCustomerEmail());
        }
        if (request.getCustomerName() != null) {
            metadata.put("customer_name", request.getCustomerName());
        }
        metadata.put("payment_type", "test_token");
        
        if (!metadata.isEmpty()) {
            paramsBuilder.putAllMetadata(metadata);
        }

        PaymentIntent intent = PaymentIntent.create(paramsBuilder.build());

        PaymentIntentResponse response = new PaymentIntentResponse(
            intent.getClientSecret(),
            intent.getId(),
            intent.getStatus(),
            intent.getAmount(),
            intent.getCurrency()
        );
        response.setDescription(intent.getDescription());
        
        return response;
    }
}