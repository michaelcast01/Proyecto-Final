package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.AdditionalInfoPayment;
import com.example.TiendaSuplementos.Repository.AdditionalInfoPaymentRepository;
import com.example.TiendaSuplementos.Repository.PaymentsRepository;
import com.example.TiendaSuplementos.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdditionalInfoPaymentService {

    @Autowired
    private AdditionalInfoPaymentRepository repository;
    
    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<AdditionalInfoPayment> findAll() {
        return repository.findAll().stream()
                .filter(payment -> payment.getActive())
                .collect(Collectors.toList());
    }

    public Optional<AdditionalInfoPayment> findById(Long id) {
        return repository.findById(id)
                .filter(payment -> payment.getActive());
    }

    public AdditionalInfoPayment save(AdditionalInfoPayment additionalInfoPayment) {
        // Validar que el paymentId no sea nulo
        if (additionalInfoPayment.getPayment_id() == null) {
            throw new RuntimeException("Payment ID is required");
        }
        
        // Validar que el pago exista
        if (!paymentsRepository.existsById(additionalInfoPayment.getPayment_id())) {
            throw new RuntimeException("Payment with ID " + additionalInfoPayment.getPayment_id() + " does not exist");
        }

        // Validar que el userId no sea nulo
        if (additionalInfoPayment.getUser_id() == null) {
            throw new RuntimeException("User ID is required");
        }

        // Validar que el usuario exista
        if (!usersRepository.existsById(additionalInfoPayment.getUser_id())) {
            throw new RuntimeException("User with ID " + additionalInfoPayment.getUser_id() + " does not exist");
        }
        
        // Validar campos requeridos
        if (additionalInfoPayment.getCountry() == null || additionalInfoPayment.getCountry().trim().isEmpty()) {
            throw new RuntimeException("Country is required");
        }
        if (additionalInfoPayment.getAddressLine1() == null || additionalInfoPayment.getAddressLine1().trim().isEmpty()) {
            throw new RuntimeException("Address Line 1 is required");
        }
        if (additionalInfoPayment.getCity() == null || additionalInfoPayment.getCity().trim().isEmpty()) {
            throw new RuntimeException("City is required");
        }
        if (additionalInfoPayment.getStateOrProvince() == null || additionalInfoPayment.getStateOrProvince().trim().isEmpty()) {
            throw new RuntimeException("State or Province is required");
        }
        if (additionalInfoPayment.getPostalCode() == null || additionalInfoPayment.getPostalCode().trim().isEmpty()) {
            throw new RuntimeException("Postal Code is required");
        }

        additionalInfoPayment.setActive(true);
        return repository.save(additionalInfoPayment);
    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(payment -> {
            payment.setActive(false);
            repository.save(payment);
        });
    }

    public AdditionalInfoPayment update(Long id, AdditionalInfoPayment additionalInfoPayment) {
        return repository.findById(id)
                .filter(payment -> payment.getActive())
                .map(existing -> {
                    if (additionalInfoPayment.getPayment_id() != null) {
                        // Validar que el nuevo paymentId exista
                        if (!paymentsRepository.existsById(additionalInfoPayment.getPayment_id())) {
                            throw new RuntimeException("Payment with ID " + additionalInfoPayment.getPayment_id() + " does not exist");
                        }
                        existing.setPayment_id(additionalInfoPayment.getPayment_id());
                    }

                    if (additionalInfoPayment.getUser_id() != null) {
                        // Validar que el nuevo userId exista
                        if (!usersRepository.existsById(additionalInfoPayment.getUser_id())) {
                            throw new RuntimeException("User with ID " + additionalInfoPayment.getUser_id() + " does not exist");
                        }
                        existing.setUser_id(additionalInfoPayment.getUser_id());
                    }

                    if (additionalInfoPayment.getCardNumber() != null) {
                        existing.setCardNumber(additionalInfoPayment.getCardNumber());
                    }
                    if (additionalInfoPayment.getExpirationDate() != null) {
                        existing.setExpirationDate(additionalInfoPayment.getExpirationDate());
                    }
                    if (additionalInfoPayment.getCvc() != null) {
                        existing.setCvc(additionalInfoPayment.getCvc());
                    }
                    if (additionalInfoPayment.getCardholderName() != null) {
                        existing.setCardholderName(additionalInfoPayment.getCardholderName());
                    }
                    if (additionalInfoPayment.getCountry() != null) {
                        existing.setCountry(additionalInfoPayment.getCountry());
                    }
                    if (additionalInfoPayment.getAddressLine1() != null) {
                        existing.setAddressLine1(additionalInfoPayment.getAddressLine1());
                    }
                    if (additionalInfoPayment.getAddressLine2() != null) {
                        existing.setAddressLine2(additionalInfoPayment.getAddressLine2());
                    }
                    if (additionalInfoPayment.getCity() != null) {
                        existing.setCity(additionalInfoPayment.getCity());
                    }
                    if (additionalInfoPayment.getStateOrProvince() != null) {
                        existing.setStateOrProvince(additionalInfoPayment.getStateOrProvince());
                    }
                    if (additionalInfoPayment.getPostalCode() != null) {
                        existing.setPostalCode(additionalInfoPayment.getPostalCode());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Additional Info Payment not found or inactive"));
    }
} 