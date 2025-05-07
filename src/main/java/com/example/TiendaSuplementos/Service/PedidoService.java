package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Pedido;
import com.example.TiendaSuplementos.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar() { return repository.findAll(); }
    public Optional<Pedido> porId(Long id) { return repository.findById(id); }
    public Pedido guardar(Pedido pedido) { return repository.save(pedido); }
    public Pedido actualizar(Long id, Pedido pedido) {
        return repository.findById(id)
                .map(p -> {
                    p.setFechaPedido(pedido.getFechaPedido());
                    p.setEstado(pedido.getEstado());
                    return repository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
    public void eliminar(Long id) { repository.deleteById(id); }
}