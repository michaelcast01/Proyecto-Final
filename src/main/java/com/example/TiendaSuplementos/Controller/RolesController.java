package com.example.TiendaSuplementos.Controller;
import com.example.TiendaSuplementos.Model.Categoria;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService service;

    @GetMapping
    public List<Roles> get() {
        return service.get();
    }

    @PostMapping
    public ResponseEntity<Roles> save(@RequestBody Roles roles) {
        Roles data = service.save(roles);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Roles> update(@PathVariable Long id, @RequestBody Roles roles) {
        Roles data = service.update(id, roles);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}