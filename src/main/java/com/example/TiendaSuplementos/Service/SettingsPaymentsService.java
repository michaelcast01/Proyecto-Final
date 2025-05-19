package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.SettingsPayments;
import com.example.TiendaSuplementos.Repository.SettingsPaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsPaymentsService {

    @Autowired
    private SettingsPaymentsRepository repository;

    public List<SettingsPayments> findAll() {
        return repository.findAll();
    }

    public Optional<SettingsPayments> findById(Long id) {
        return repository.findById(id);
    }

    public SettingsPayments create(SettingsPayments settingsPayments) {
        return repository.save(settingsPayments);
    }

    public SettingsPayments update(Long id, SettingsPayments settingsPayments) {
        return repository.findById(id)
                .map(existing -> {
                    if (settingsPayments.getSettings_id() != null) {
                        existing.setSettings_id(settingsPayments.getSettings_id());
                    }
                    if (settingsPayments.getPayment_id() != null) {
                        existing.setPayment_id(settingsPayments.getPayment_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Settings-Payment relationship not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
} 