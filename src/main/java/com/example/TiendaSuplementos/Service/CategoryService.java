package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Category;
import com.example.TiendaSuplementos.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> get() { return repository.findAll(); }
    public Optional<Category> getById(Long id) { return repository.findById(id); }
    public Category save(Category category) { return repository.save(category); }
    public Category update(Long id, Category category) {
        return repository.findById(id)
                .map(c -> {
                    c.setName(category.getName());
                    c.setDescription(category.getDescription());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no found"));
    }
    public void delete(Long id) { repository.deleteById(id); }
}