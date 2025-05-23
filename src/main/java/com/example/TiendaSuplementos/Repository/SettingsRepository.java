package com.example.TiendaSuplementos.Repository;


import com.example.TiendaSuplementos.Model.Settings;
import com.example.TiendaSuplementos.Model.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> { }