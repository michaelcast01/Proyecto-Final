package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Producto;
import com.example.TiendaSuplementos.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> listar() { return repository.findAll(); }
    public Optional<Producto> porId(Long id) { return repository.findById(id); }
    public Producto guardar(Producto producto) { return repository.save(producto); }
    public Producto actualizar(Long id, Producto producto) {
        return repository.findById(id)
                .map(p -> {
                    p.setNombre(producto.getNombre());
                    p.setDescripcion(producto.getDescripcion());
                    p.setPrecio(producto.getPrecio());
                    p.setStock(producto.getStock());
                    p.setCategoria(producto.getCategoria());
                    return repository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    public void eliminar(Long id) { repository.deleteById(id); }
}