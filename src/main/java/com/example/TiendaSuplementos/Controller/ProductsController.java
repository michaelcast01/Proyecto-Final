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
    public List<Products> get(@RequestParam(required = false, defaultValue = "false") Boolean include_disabled) {
        // Por defecto, devuelve solo productos habilitados
        // Si include_disabled=true, devuelve todos (requiere ser admin, pero se verifica en el frontend)
        if (include_disabled != null && include_disabled) {
            return service.get();
        }
        return service.getEnabled();
    }

    @PostMapping
    public ResponseEntity<Products> save(@RequestBody Products products) {
        Products data = service.save(products);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getById(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean include_disabled) {
        // Para clientes, solo devolver productos habilitados
        if (include_disabled != null && include_disabled) {
            return service.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return service.getByIdEnabled(id)
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
    
    // Endpoint para alternar el estado enabled/disabled de un producto
    // NOTA: En producción, este endpoint debería estar protegido y solo accesible para ADMIN
    @PatchMapping("/{id}/toggle-enabled")
    public ResponseEntity<Products> toggleEnabled(@PathVariable Long id) {
        try {
            Products updated = service.toggleEnabled(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> getStock(@PathVariable Long id) {
        return service.getById(id)
                .map(product -> ResponseEntity.ok(product.getStock()))
                .orElse(ResponseEntity.notFound().build());
    }
} 