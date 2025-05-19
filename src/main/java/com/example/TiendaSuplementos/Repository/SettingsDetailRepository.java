package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.SettingsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsDetailRepository extends JpaRepository<SettingsDetail, Long> {
} 