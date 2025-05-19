package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.OrderProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetail, Long> {
} 