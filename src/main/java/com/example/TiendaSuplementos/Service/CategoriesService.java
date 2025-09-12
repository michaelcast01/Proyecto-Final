package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Categories;
import com.example.TiendaSuplementos.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository repository;

    public List<Categories> get() { return repository.findAll(); }
    public Optional<Categories> getById(Long id) { return repository.findById(id); }
    public Categories save(Categories categories) { return repository.save(categories); }
    public Categories update(Long id, Categories categories) {
        return repository.findById(id)
                .map(c -> {
                    c.setName(categories.getName());
                    c.setDescription(categories.getDescription());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no found"));
    }
    public void delete(Long id) { repository.deleteById(id); }
}