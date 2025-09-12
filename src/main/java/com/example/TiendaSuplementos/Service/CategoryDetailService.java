package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.CategoryDetail;
import com.example.TiendaSuplementos.Repository.CategoryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryDetailService {
    @Autowired
    private CategoryDetailRepository repository;

    public List<CategoryDetail> findAll() {
        return repository.findAll();
    }

    public Optional<CategoryDetail> findById(Long id) {
        return repository.findById(id);
    }
} 