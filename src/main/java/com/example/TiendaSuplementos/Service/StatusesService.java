package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Model.Statuses;
import com.example.TiendaSuplementos.Repository.RolesRepository;
import com.example.TiendaSuplementos.Repository.StatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusesService {

    @Autowired
    private StatusesRepository repository;

    // GET "Obtener Data"
    public Optional<Statuses> getById(Long id) {
        return repository.findById(id);
    }
    public List<Statuses> get() { return repository.findAll(); }
    // SAVE Guardar
    public Statuses save(Statuses statuses) { return repository.save(statuses); }
    // DELETE
    public void delete(Long id) { repository.deleteById(id); }
    public Statuses update(Long id, Statuses statuses) {
        return repository.findById(id)
                .map(c -> {
                    c.setName(statuses.getName());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Statuses not found"));
    }
}
