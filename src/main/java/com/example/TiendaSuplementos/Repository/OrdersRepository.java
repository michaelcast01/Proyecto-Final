package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
} 