package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.UserDetail;
import com.example.TiendaSuplementos.Repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserDetailRepository getRepository() {
        return repository;
    }

    public void setRepository(UserDetailRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<UserDetail> findAll() {
        String jpql = "SELECT DISTINCT u FROM UserDetail u " +
                     "LEFT JOIN FETCH u.orders o " +
                     "LEFT JOIN FETCH o.products";
        TypedQuery<UserDetail> query = entityManager.createQuery(jpql, UserDetail.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<UserDetail> findById(Long id) {
        String jpql = "SELECT DISTINCT u FROM UserDetail u " +
                     "LEFT JOIN FETCH u.orders o " +
                     "LEFT JOIN FETCH o.products " +
                     "WHERE u.id = :id";
        TypedQuery<UserDetail> query = entityManager.createQuery(jpql, UserDetail.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    public UserDetail findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserDetail create(UserDetail users) {
        return repository.save(users);
    }

    public UserDetail update(Long id, UserDetail userDetail) {
        return repository.findById(id)
                .map(existing -> {
                    if (userDetail.getUsername() != null) {
                        existing.setUsername(userDetail.getUsername());
                    }
                    if (userDetail.getEmail() != null) {
                        existing.setEmail(userDetail.getEmail());
                    }
                    if (userDetail.getPassword() != null) {
                        existing.setPassword(userDetail.getPassword());
                    }
                    if (userDetail.getRole_id() != null) {
                        existing.setRole_id(userDetail.getRole_id());
                    }
                    if (userDetail.getSetting_id() != null) {
                        existing.setSetting_id(userDetail.getSetting_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<UserDetail> login(String email, String password) {
        UserDetail user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Transactional
    public List<UserDetail> findByStatusId(Long statusId) {
        String jpql = "SELECT DISTINCT u FROM UserDetail u " +
                     "LEFT JOIN FETCH u.orders o " +
                     "LEFT JOIN FETCH o.products " +
                     "WHERE o.status_id = :statusId";
        TypedQuery<UserDetail> query = entityManager.createQuery(jpql, UserDetail.class);
        query.setParameter("statusId", statusId);
        return query.getResultList();
    }
}
