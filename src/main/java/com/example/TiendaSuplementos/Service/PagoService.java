package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Pago;
import com.example.TiendaSuplementos.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    public List<Pago> listar() {
        return repository.findAll();
    }

    public Optional<Pago> porId(Long id) {
        return repository.findById(id);
    }

    public Pago guardar(Pago pago) {
        return repository.save(pago);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
