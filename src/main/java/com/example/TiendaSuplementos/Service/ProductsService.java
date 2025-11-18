package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Products;
import com.example.TiendaSuplementos.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository repository;

    public Optional<Products> getById(Long id) {
        return repository.findById(id);
    }
    
    public Optional<Products> getByIdEnabled(Long id) {
        return repository.findByIdAndEnabledTrue(id);
    }

    public List<Products> get() {
        return repository.findAll();
    }
    
    public List<Products> getEnabled() {
        return repository.findByEnabledTrue();
    }

    public Products save(Products products) {
        return repository.save(products);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Products update(Long id, Products products) {
        return repository.findById(id)
                .map(existing -> {
                    if (products.getName() != null) {
                        existing.setName(products.getName());
                    }
                    if (products.getDescription() != null) {
                        existing.setDescription(products.getDescription());
                    }
                    if (products.getPrice() != null) {
                        existing.setPrice(products.getPrice());
                    }
                    if (products.getStock() != null) {
                        existing.setStock(products.getStock());
                    }
                    if (products.getUrl_image() != null) {
                        existing.setUrl_image(products.getUrl_image());
                    }
                    if (products.getEnabled() != null) {
                        existing.setEnabled(products.getEnabled());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public Products toggleEnabled(Long id) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setEnabled(!existing.getEnabled());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
} 