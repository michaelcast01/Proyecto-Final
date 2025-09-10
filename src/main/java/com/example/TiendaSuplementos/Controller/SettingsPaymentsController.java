package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.SettingsPayments;
import com.example.TiendaSuplementos.Service.SettingsPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/settings-payments")
public class SettingsPaymentsController {

    @Autowired
    private SettingsPaymentsService service;

    @GetMapping
    public List<SettingsPayments> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingsPayments> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SettingsPayments> create(@RequestBody SettingsPayments settingsPayments) {
        SettingsPayments created = service.create(settingsPayments);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SettingsPayments> update(@PathVariable Long id, @RequestBody SettingsPayments settingsPayments) {
        SettingsPayments updated = service.update(id, settingsPayments);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 