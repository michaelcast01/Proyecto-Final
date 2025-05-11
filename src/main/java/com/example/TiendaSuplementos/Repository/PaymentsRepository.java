package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Service.PaymentsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> { }
