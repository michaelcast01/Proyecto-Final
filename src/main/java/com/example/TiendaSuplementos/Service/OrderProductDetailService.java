package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.OrderProductDetail;
import com.example.TiendaSuplementos.Repository.OrderProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductDetailService {
    @Autowired
    private OrderProductDetailRepository repository;

    public List<OrderProductDetail> findAll() {
        return repository.findAll();
    }

    public Optional<OrderProductDetail> findById(Long id) {
        return repository.findById(id);
    }
} 