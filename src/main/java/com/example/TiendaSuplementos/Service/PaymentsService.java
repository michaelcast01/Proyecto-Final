package com.example.TiendaSuplementos.Service;


import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Repository.PaymentsRepository;
import com.example.TiendaSuplementos.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

        @Autowired
        private PaymentsRepository repository;

        public Optional<Payments> getById(Long id) {
            return repository.findById(id);
        }

        public List<Payments> get() {
            return repository.findAll();
        }

        public Payments save(Payments payments) {
            return repository.save(payments);
        }

        public void delete(Long id) {
            repository.deleteById(id);
        }

        public Payments update(Long id, Payments payments) {
            return repository.findById(id)
                    .map(p -> {
                        p.setName(payments.getName());
                        return repository.save(p);
                    })
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        }

    }
