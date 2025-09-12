package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.OrderDetail;
import com.example.TiendaSuplementos.Repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<OrderDetail> findAll() {
        String jpql = "SELECT DISTINCT o FROM OrderDetail o LEFT JOIN FETCH o.products";
        TypedQuery<OrderDetail> query = entityManager.createQuery(jpql, OrderDetail.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<OrderDetail> findById(Long id) {
        String jpql = "SELECT DISTINCT o FROM OrderDetail o LEFT JOIN FETCH o.products WHERE o.order_id = :id";
        TypedQuery<OrderDetail> query = entityManager.createQuery(jpql, OrderDetail.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }
} 