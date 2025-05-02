package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
