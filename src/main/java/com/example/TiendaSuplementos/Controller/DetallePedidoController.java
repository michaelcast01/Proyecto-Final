package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.DetallePedido;
import com.example.TiendaSuplementos.Service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    public List<DetallePedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> porId(@PathVariable Long id) {
        return service.porId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetallePedido> crear(@RequestBody DetallePedido detalle) {
        DetallePedido creado = service.guardar(detalle);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Long id, @RequestBody DetallePedido detalle) {
        DetallePedido actualizado = service.actualizar(id, detalle);
        return	ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}