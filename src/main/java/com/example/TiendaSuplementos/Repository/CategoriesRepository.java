package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> { }