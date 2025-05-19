package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.SettingsPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsPaymentsRepository extends JpaRepository<SettingsPayments, Long> {
} 