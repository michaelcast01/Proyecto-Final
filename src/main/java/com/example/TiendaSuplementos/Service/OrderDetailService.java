package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.OrderDetail;
import com.example.TiendaSuplementos.Repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository repository;

    public List<OrderDetail> findAll() {
        return repository.findAll();
    }

    public Optional<OrderDetail> findById(Long id) {
        return repository.findById(id);
    }
} 