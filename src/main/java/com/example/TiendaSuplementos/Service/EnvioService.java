package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Envio;
import com.example.TiendaSuplementos.Repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    public List<Envio> listar() { return repository.findAll(); }
    public Optional<Envio> porId(Long id) { return repository.findById(id); }
    public Envio guardar(Envio envio) { return repository.save(envio); }
    public Envio actualizar(Long id, Envio envio) {
        return repository.findById(id)
                .map(e -> {
                    e.setDireccion(envio.getDireccion());
                    e.setCiudad(envio.getCiudad());
                    e.setProvincia(envio.getProvincia());
                    e.setCodigoPostal(envio.getCodigoPostal());
                    e.setFechaEnvio(envio.getFechaEnvio());
                    e.setFechaEntrega(envio.getFechaEntrega());
                    e.setTransportadora(envio.getTransportadora());
                    return repository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
    }
    public void eliminar(Long id) { repository.deleteById(id); }
}