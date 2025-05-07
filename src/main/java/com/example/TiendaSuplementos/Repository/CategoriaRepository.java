package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> { }