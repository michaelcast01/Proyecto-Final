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
    public Envio crear(@RequestBody Envio envio) {
        return service.guardar(envio);
    }

    @PutMapping("/{id}")
    public Envio actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        envio.setId(id);
        return service.guardar(envio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
