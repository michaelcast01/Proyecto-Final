package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}