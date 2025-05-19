package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.OrderProductDetail;
import com.example.TiendaSuplementos.Service.OrderProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-product-details")
public class OrderProductDetailController {
    @Autowired
    private OrderProductDetailService service;

    @GetMapping
    public List<OrderProductDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProductDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 