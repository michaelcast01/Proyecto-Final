package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.AdditionalInfoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalInfoPaymentDetailRepository extends JpaRepository<AdditionalInfoPaymentDetail, Long> {
} 