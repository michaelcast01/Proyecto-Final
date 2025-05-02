package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Envio;
import com.example.TiendaSuplementos.Repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    public List<Envio> listar() {
        return repository.findAll();
    }

    public Optional<Envio> porId(Long id) {
        return repository.findById(id);
    }

    public Envio guardar(Envio envio) {
        return repository.save(envio);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
