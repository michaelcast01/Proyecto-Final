package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> { }
