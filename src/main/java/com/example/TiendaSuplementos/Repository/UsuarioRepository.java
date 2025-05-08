package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }
