package com.example.TiendaSuplementos.Controller;
import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService service;

    @GetMapping
    public List<Payments> get() {
        return service.get();
    }

    @PostMapping
    public ResponseEntity<Payments> save(@RequestBody Payments payments) {
        Payments data = service.save(payments);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payments> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payments> update(@PathVariable Long id, @RequestBody Payments payments) {
        Payments data = service.update(id, payments);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
