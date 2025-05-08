package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Usuario;
import com.example.TiendaSuplementos.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        // al persistir, cascada guardará los pedidos y detalles anidados
        return repository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuario) {
        return repository.findById(id)
                .map(u -> {
                    u.setCorreo(usuario.getCorreo());
                    u.setContrasenaHash(usuario.getContrasenaHash());
                    u.setNombreCompleto(usuario.getNombreCompleto());
                    u.setRol(usuario.getRol());
                    u.setFechaCreacion(usuario.getFechaCreacion());
                    u.setPedidos(usuario.getPedidos());
                    return repository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
