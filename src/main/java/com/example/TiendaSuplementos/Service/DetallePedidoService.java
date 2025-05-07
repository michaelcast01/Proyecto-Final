package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.DetallePedido;
import com.example.TiendaSuplementos.Repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    public List<DetallePedido> listar() { return repository.findAll(); }
    public Optional<DetallePedido> porId(Long id) { return repository.findById(id); }
    public DetallePedido guardar(DetallePedido detalle) { return repository.save(detalle); }
    public DetallePedido actualizar(Long id, DetallePedido detalle) {
        return repository.findById(id)
                .map(d -> {
                    d.setCantidad(detalle.getCantidad());
                    d.setPrecioUnitario(detalle.getPrecioUnitario());
                    d.setProducto(detalle.getProducto());
                    return repository.save(d);
                })
                .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
    }
    public void eliminar(Long id) { repository.deleteById(id); }
}