package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.CategoryDetail;
import com.example.TiendaSuplementos.Service.CategoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-details")
public class CategoryDetailController {
    @Autowired
    private CategoryDetailService service;

    @GetMapping
    public List<CategoryDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 