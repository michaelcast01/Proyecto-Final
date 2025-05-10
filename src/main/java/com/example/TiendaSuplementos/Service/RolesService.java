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

    public Optional<Roles> porId(Long id) {
        return repository.findById(id);
    }

    public Roles guardar(Roles roles) { return repository.save(roles); }

}