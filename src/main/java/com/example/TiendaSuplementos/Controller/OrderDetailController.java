package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.OrderDetail;
import com.example.TiendaSuplementos.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService service;

    @GetMapping
    public List<OrderDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 