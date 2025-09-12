package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.CategoriesProducts;
import com.example.TiendaSuplementos.Repository.CategoriesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesProductsService {

    @Autowired
    private CategoriesProductsRepository repository;

    public List<CategoriesProducts> get() {
        return repository.findAll();
    }

    public Optional<CategoriesProducts> getById(Long id) {
        return repository.findById(id);
    }

    public CategoriesProducts save(CategoriesProducts categoriesProducts) {
        return repository.save(categoriesProducts);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public CategoriesProducts update(Long id, CategoriesProducts categoriesProducts) {
        return repository.findById(id)
                .map(existing -> {
                    if (categoriesProducts.getCategory_id() != null) {
                        existing.setCategory_id(categoriesProducts.getCategory_id());
                    }
                    if (categoriesProducts.getProduct_id() != null) {
                        existing.setProduct_id(categoriesProducts.getProduct_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Relación categoría-producto no encontrada"));
    }
} 