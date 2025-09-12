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
                     "LEFT JOIN FETCH a.user u " +
                     "WHERE a.active = true";
        TypedQuery<AdditionalInfoPaymentDetail> query = entityManager.createQuery(jpql, AdditionalInfoPaymentDetail.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<AdditionalInfoPaymentDetail> findById(Long id) {
        String jpql = "SELECT DISTINCT a FROM AdditionalInfoPaymentDetail a " +
                     "LEFT JOIN FETCH a.payment p " +
                     "LEFT JOIN FETCH a.user u " +
                     "WHERE a.id = :id AND a.active = true";
        TypedQuery<AdditionalInfoPaymentDetail> query = entityManager.createQuery(jpql, AdditionalInfoPaymentDetail.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Transactional
    public List<AdditionalInfoPaymentDetail> findByUserId(Long userId) {
        String jpql = "SELECT DISTINCT a FROM AdditionalInfoPaymentDetail a " +
                     "LEFT JOIN FETCH a.payment p " +
                     "LEFT JOIN FETCH a.user u " +
                     "WHERE a.user_id = :userId AND a.active = true";
        TypedQuery<AdditionalInfoPaymentDetail> query = entityManager.createQuery(jpql, AdditionalInfoPaymentDetail.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    public void delete(Long id) {
        String jpql = "UPDATE AdditionalInfoPaymentDetail a SET a.active = false WHERE a.id = :id";
        entityManager.createQuery(jpql)
                    .setParameter("id", id)
                    .executeUpdate();
    }

    @Transactional
    public AdditionalInfoPaymentDetail save(AdditionalInfoPaymentDetail detail) {
        detail.setActive(Boolean.TRUE);
        return repository.save(detail);
    }

    @Transactional
    public AdditionalInfoPaymentDetail update(Long id, AdditionalInfoPaymentDetail detail) {
        return findById(id)
                .map(existing -> {
                    if (detail.getPayment_id() != null) {
                        existing.setPayment_id(detail.getPayment_id());
                    }
                    if (detail.getUser_id() != null) {
                        existing.setUser_id(detail.getUser_id());
                    }
                    if (detail.getCardNumber() != null) {
                        existing.setCardNumber(detail.getCardNumber());
                    }
                    if (detail.getExpirationDate() != null) {
                        existing.setExpirationDate(detail.getExpirationDate());
                    }
                    if (detail.getCvc() != null) {
                        existing.setCvc(detail.getCvc());
                    }
                    if (detail.getCardholderName() != null) {
                        existing.setCardholderName(detail.getCardholderName());
                    }
                    if (detail.getCountry() != null) {
                        existing.setCountry(detail.getCountry());
                    }
                    if (detail.getAddressLine1() != null) {
                        existing.setAddressLine1(detail.getAddressLine1());
                    }
                    if (detail.getAddressLine2() != null) {
                        existing.setAddressLine2(detail.getAddressLine2());
                    }
                    if (detail.getCity() != null) {
                        existing.setCity(detail.getCity());
                    }
                    if (detail.getStateOrProvince() != null) {
                        existing.setStateOrProvince(detail.getStateOrProvince());
                    }
                    if (detail.getPostalCode() != null) {
                        existing.setPostalCode(detail.getPostalCode());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payment Detail not found or inactive"));
    }
} 