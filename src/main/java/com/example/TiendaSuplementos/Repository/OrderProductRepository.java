package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    
    @Query("SELECT op FROM OrderProduct op WHERE op.order_id = :orderId")
    List<OrderProduct> findByOrderId(@Param("orderId") Long orderId);
} 