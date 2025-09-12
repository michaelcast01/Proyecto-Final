package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.AdditionalInfoPaymentDetail;
import com.example.TiendaSuplementos.Service.AdditionalInfoPaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/additional-info-payments-details")
public class AdditionalInfoPaymentDetailController {
    @Autowired
    private AdditionalInfoPaymentDetailService service;

    @GetMapping
    public List<AdditionalInfoPaymentDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalInfoPaymentDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<AdditionalInfoPaymentDetail> getByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }
} 