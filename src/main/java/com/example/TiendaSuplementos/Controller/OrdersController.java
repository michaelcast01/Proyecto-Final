package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.Orders;
import com.example.TiendaSuplementos.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @GetMapping
    public List<Orders> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Orders> save(@RequestBody Orders orders) {
        Orders data = service.save(orders);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> update(@PathVariable Long id, @RequestBody Orders orders) {
        Orders data = service.update(id, orders);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 