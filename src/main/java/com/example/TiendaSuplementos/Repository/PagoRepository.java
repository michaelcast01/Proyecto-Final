package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> { }
