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

    /**
     * Creates a PaymentIntent with Stripe and optionally confirms it with card details
     * @param request PaymentIntentRequest containing amount, currency and other details
     * @return PaymentIntentResponse with client secret and payment details
     * @throws StripeException if there's an error with Stripe API
     */
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

        // Add metadata if customer information is provided
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

        // Create the PaymentIntent
        PaymentIntent intent = PaymentIntent.create(paramsBuilder.build());

        // If card details are provided, create payment method and confirm payment
        if (request.getCard() != null) {
            try {
                // Create payment method with card details
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

                // Confirm the payment intent with the payment method
                PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                    .setPaymentMethod(paymentMethod.getId())
                    .build();

                intent = intent.confirm(confirmParams);
                
            } catch (Exception e) {
                // If card processing fails, return the intent as is for client-side handling
                System.err.println("Error processing card: " + e.getMessage());
            }
        }

        // Return response with client secret
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

    /**
     * Retrieves a PaymentIntent from Stripe
     * @param paymentIntentId The ID of the PaymentIntent to retrieve
     * @return PaymentIntentResponse with payment details
     * @throws StripeException if there's an error with Stripe API
     */
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

    /**
     * Confirms a PaymentIntent (useful for server-side confirmation)
     * @param paymentIntentId The ID of the PaymentIntent to confirm
     * @return PaymentIntentResponse with updated payment details
     * @throws StripeException if there's an error with Stripe API
     */
    public PaymentIntentResponse confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        
        // Confirm the PaymentIntent
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

    /**
     * Confirms a PaymentIntent with card details
     * @param paymentIntentId The ID of the PaymentIntent to confirm
     * @param cardDetails The card details to use for payment
     * @return PaymentIntentResponse with updated payment details
     * @throws StripeException if there's an error with Stripe API
     */
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

        // Confirm the payment intent with the payment method
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

    /**
     * Creates and confirms a payment using safe test tokens
     * @param request TestPaymentRequest with amount and test token
     * @return PaymentIntentResponse with payment details
     * @throws StripeException if there's an error with Stripe API
     */
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

        // Create payment intent
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

        // Add metadata if customer information is provided
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

        // Create and confirm the payment intent
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