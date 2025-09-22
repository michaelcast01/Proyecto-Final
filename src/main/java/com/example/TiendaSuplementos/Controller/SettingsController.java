package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.Settings;
import com.example.TiendaSuplementos.Service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SettingsController{

    @Autowired
    private SettingsService service;

    @GetMapping
    public List<Settings> get() {
        return service.get();
    }

    @PostMapping
    public ResponseEntity<Settings> save(@RequestBody Settings settings) {
        Settings data = service.save(settings);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Settings> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Settings> update(@PathVariable Long id, @RequestBody Settings settings) {
        Settings data = service.update(id, settings);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}