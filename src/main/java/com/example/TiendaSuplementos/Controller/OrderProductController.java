package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.OrderProduct;
import com.example.TiendaSuplementos.Service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-products")
public class OrderProductController {

    @Autowired
    private OrderProductService service;

    @GetMapping
    public List<OrderProduct> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderProduct> save(@RequestBody OrderProduct orderProduct) {
        OrderProduct data = service.save(orderProduct);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderProduct> update(@PathVariable Long id, @RequestBody OrderProduct orderProduct) {
        OrderProduct data = service.update(id, orderProduct);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
