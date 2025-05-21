package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.AdditionalInfoPayment;
import com.example.TiendaSuplementos.Service.AdditionalInfoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/additional-info-payments")
@CrossOrigin(originPatterns = "*", allowCredentials = "false")
public class AdditionalInfoPaymentController {

    @Autowired
    private AdditionalInfoPaymentService service;

    @GetMapping
    public List<AdditionalInfoPayment> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalInfoPayment> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AdditionalInfoPayment additionalInfoPayment) {
        try {
            AdditionalInfoPayment created = service.save(additionalInfoPayment);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdditionalInfoPayment additionalInfoPayment) {
        try {
            AdditionalInfoPayment updated = service.update(id, additionalInfoPayment);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (service.findById(id).isPresent()) {
                service.delete(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 