package com.example.TiendaSuplementos.Controller;
import com.example.TiendaSuplementos.Service.CategoriesService;

import com.example.TiendaSuplementos.Model.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService service;

    @GetMapping
    public List<Categories> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categories> save(@RequestBody Categories categories) {
        Categories data = service.save(categories);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categories> update(@PathVariable Long id, @RequestBody Categories categories) {
        Categories data = service.update(id, categories);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}