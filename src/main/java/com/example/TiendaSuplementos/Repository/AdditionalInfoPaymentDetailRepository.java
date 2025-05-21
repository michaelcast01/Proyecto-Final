package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.AdditionalInfoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalInfoPaymentDetailRepository extends JpaRepository<AdditionalInfoPaymentDetail, Long> {
    List<AdditionalInfoPaymentDetail> findByUser_id(Long userId);
} 