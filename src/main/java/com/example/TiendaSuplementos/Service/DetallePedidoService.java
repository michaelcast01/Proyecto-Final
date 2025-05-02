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

    public List<DetallePedido> listar() {
        return repository.findAll();
    }

    public Optional<DetallePedido> porId(Long id) {
        return repository.findById(id);
    }

    public DetallePedido guardar(DetallePedido detalle) {
        return repository.save(detalle);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
