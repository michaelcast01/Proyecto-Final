package com.example.TiendaSuplementos.Service;


import com.example.TiendaSuplementos.Model.User;
import com.example.TiendaSuplementos.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }


    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }


    public User create(User user) {
        return repository.save(user);
    }


    public User update(Long id, User user) {
        return repository.findById(id)
                .map(existing -> {
                    if (user.getUsername() != null) {
                        existing.setUsername(user.getUsername());
                    }
                    if (user.getEmail() != null) {
                        existing.setEmail(user.getEmail());
                    }
                    if (user.getPassword() != null) {
                        existing.setPassword(user.getPassword());
                    }
                    if (user.getRole_id() != null) {
                        existing.setRole_id(user.getRole_id());
                    }
                    if (user.getSetting_id() != null) {
                        existing.setSetting_id(user.getSetting_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}
