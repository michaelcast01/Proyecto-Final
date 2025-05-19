package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.SettingsDetail;
import com.example.TiendaSuplementos.Service.SettingsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings-details")
public class SettingsDetailController {

    @Autowired
    private SettingsDetailService service;

    @GetMapping
    public List<SettingsDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingsDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{settingsId}/payments/{paymentId}")
    public ResponseEntity<SettingsDetail> addPayment(
            @PathVariable Long settingsId,
            @PathVariable Long paymentId) {
        SettingsDetail updated = service.addPayment(settingsId, paymentId);
        return ResponseEntity.ok(updated);
    }
} 