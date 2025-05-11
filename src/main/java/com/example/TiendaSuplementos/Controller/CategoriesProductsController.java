package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.CategoriesProducts;
import com.example.TiendaSuplementos.Service.CategoriesProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories-products")
public class CategoriesProductsController {

    @Autowired
    private CategoriesProductsService service;

    @GetMapping
    public List<CategoriesProducts> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesProducts> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriesProducts> save(@RequestBody CategoriesProducts categoriesProducts) {
        CategoriesProducts data = service.save(categoriesProducts);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesProducts> update(@PathVariable Long id, @RequestBody CategoriesProducts categoriesProducts) {
        CategoriesProducts data = service.update(id, categoriesProducts);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 