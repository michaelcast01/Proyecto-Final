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
    public DetallePedido crear(@RequestBody DetallePedido detalle) {
        return service.guardar(detalle);
    }

    @PutMapping("/{id}")
    public DetallePedido actualizar(@PathVariable Long id, @RequestBody DetallePedido detalle) {
        detalle.setId(id);
        return service.guardar(detalle);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
