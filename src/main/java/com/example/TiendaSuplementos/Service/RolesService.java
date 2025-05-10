package com.example.TiendaSuplementos.Service;


import com.example.TiendaSuplementos.Model.Categoria;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Repository.CategoriaRepository;
import com.example.TiendaSuplementos.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RolesService {

    @Autowired
    private RolesRepository repository;

    // GET "Obtener Data"
    public Optional<Roles> getById(Long id) {
        return repository.findById(id);
    }
    public List<Roles> get() { return repository.findAll(); }
    // SAVE Guardar
    public Roles save(Roles roles) { return repository.save(roles); }
    // DELETE
    public void delete(Long id) { repository.deleteById(id); }
    public Roles update(Long id, Roles roles) {
        return repository.findById(id)
                .map(c -> {
                    c.setName(roles.getName());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}