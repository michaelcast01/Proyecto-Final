package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.AdditionalInfoPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalInfoPaymentRepository extends JpaRepository<AdditionalInfoPayment, Long> {
} 