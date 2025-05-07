package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.Envio;
import com.example.TiendaSuplementos.Service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService service;

    @GetMapping
    public List<Envio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> porId(@PathVariable Long id) {
        return service.porId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Envio> crear(@RequestBody Envio envio) {
        Envio creado = service.guardar(envio);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = service.actualizar(id, envio);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}