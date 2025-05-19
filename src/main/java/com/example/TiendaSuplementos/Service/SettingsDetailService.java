package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.SettingsDetail;
import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Repository.SettingsDetailRepository;
import com.example.TiendaSuplementos.Repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SettingsDetailService {

    @Autowired
    private SettingsDetailRepository repository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    public List<SettingsDetail> findAll() {
        return repository.findAll();
    }

    public Optional<SettingsDetail> findById(Long id) {
        return repository.findById(id);
    }

    public SettingsDetail create(SettingsDetail settingsDetail) {
        return repository.save(settingsDetail);
    }

    public SettingsDetail update(Long id, SettingsDetail settingsDetail) {
        return repository.findById(id)
                .map(existing -> {
                    if (settingsDetail.getName() != null) {
                        existing.setName(settingsDetail.getName());
                    }
                    if (settingsDetail.getNickname() != null) {
                        existing.setNickname(settingsDetail.getNickname());
                    }
                    if (settingsDetail.getCity() != null) {
                        existing.setCity(settingsDetail.getCity());
                    }
                    if (settingsDetail.getPhone() != null) {
                        existing.setPhone(settingsDetail.getPhone());
                    }
                    if (settingsDetail.getAddress() != null) {
                        existing.setAddress(settingsDetail.getAddress());
                    }
                    if (settingsDetail.getPayments() != null) {
                        existing.setPayments(settingsDetail.getPayments());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Settings not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SettingsDetail addPayment(Long settingsId, Long paymentId) {
        SettingsDetail settings = repository.findById(settingsId)
                .orElseThrow(() -> new RuntimeException("Settings not found"));
        
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        settings.getPayments().add(payment);
        return repository.save(settings);
    }

    public SettingsDetail removePayment(Long settingsId, Long paymentId) {
        SettingsDetail settings = repository.findById(settingsId)
                .orElseThrow(() -> new RuntimeException("Settings not found"));
        
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        settings.getPayments().remove(payment);
        return repository.save(settings);
    }
} 