package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.AdditionalInfoPaymentDetail;
import com.example.TiendaSuplementos.Repository.AdditionalInfoPaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionalInfoPaymentDetailService {
    @Autowired
    private AdditionalInfoPaymentDetailRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<AdditionalInfoPaymentDetail> findAll() {
        String jpql = "SELECT DISTINCT a FROM AdditionalInfoPaymentDetail a " +
                     "LEFT JOIN FETCH a.payment p " +
                     "LEFT JOIN FETCH a.user u";
        TypedQuery<AdditionalInfoPaymentDetail> query = entityManager.createQuery(jpql, AdditionalInfoPaymentDetail.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<AdditionalInfoPaymentDetail> findById(Long id) {
        String jpql = "SELECT DISTINCT a FROM AdditionalInfoPaymentDetail a " +
                     "LEFT JOIN FETCH a.payment p " +
                     "LEFT JOIN FETCH a.user u " +
                     "WHERE a.id = :id";
        TypedQuery<AdditionalInfoPaymentDetail> query = entityManager.createQuery(jpql, AdditionalInfoPaymentDetail.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }
} 