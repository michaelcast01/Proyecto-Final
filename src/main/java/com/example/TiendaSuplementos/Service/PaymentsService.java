package com.example.TiendaSuplementos.Service;


import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Repository.PaymentsRepository;
import com.example.TiendaSuplementos.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentsService {

        @Autowired
        private PaymentsRepository repository;

        public Optional<Payments> porId(Long id) {
            return repository.findById(id);
        }

        public Payments guardar(Payments payments) { return repository.save(payments); }

    }
