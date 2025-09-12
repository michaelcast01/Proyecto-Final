package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.Products;
import com.example.TiendaSuplementos.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService service;

    @GetMapping
    public List<Products> get() {
        return service.get();
    }

    @PostMapping
    public ResponseEntity<Products> save(@RequestBody Products products) {
        Products data = service.save(products);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> update(@PathVariable Long id, @RequestBody Products products) {
        Products data = service.update(id, products);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 