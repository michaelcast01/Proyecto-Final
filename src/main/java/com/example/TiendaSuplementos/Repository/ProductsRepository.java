package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
} 