package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.Statuses;
import com.example.TiendaSuplementos.Service.StatusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/statuses")
public class StatusesController {

    @Autowired
    private StatusesService service;

    @GetMapping
    public List<Statuses> get() {
        return service.get();
    }

    @PostMapping
    public ResponseEntity<Statuses> save(@RequestBody Statuses statuses) {
        Statuses data = service.save(statuses);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Statuses> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Statuses> update(@PathVariable Long id, @RequestBody Statuses statuses) {
        Statuses data = service.update(id, statuses);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
