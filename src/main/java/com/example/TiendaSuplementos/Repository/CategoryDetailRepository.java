package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
} 