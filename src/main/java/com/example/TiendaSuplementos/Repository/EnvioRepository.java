package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> { }

