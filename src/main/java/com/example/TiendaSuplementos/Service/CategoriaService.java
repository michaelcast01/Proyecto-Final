package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Categoria;
import com.example.TiendaSuplementos.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Optional<Categoria> porId(Long id) {
        return repository.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return repository.save(categoria);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
