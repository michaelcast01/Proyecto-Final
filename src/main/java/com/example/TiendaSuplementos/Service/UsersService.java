package com.example.TiendaSuplementos.Service;


import com.example.TiendaSuplementos.Model.Users;
import com.example.TiendaSuplementos.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public UsersRepository getRepository() {
        return repository;
    }

    public void setRepository(UsersRepository repository) {
        this.repository = repository;
    }

    public List<Users> findAll() {
        return repository.findAll();
    }


    public Optional<Users> findById(Long id) {
        return repository.findById(id);
    }


    public Users create(Users users) {
        return repository.save(users);
    }


    public Users update(Long id, Users users) {
        return repository.findById(id)
                .map(existing -> {
                    if (users.getUsername() != null) {
                        existing.setUsername(users.getUsername());
                    }
                    if (users.getEmail() != null) {
                        existing.setEmail(users.getEmail());
                    }
                    if (users.getPassword() != null) {
                        existing.setPassword(users.getPassword());
                    }
                    if (users.getRole_id() != null) {
                        existing.setRole_id(users.getRole_id());
                    }
                    if (users.getSetting_id() != null) {
                        existing.setSetting_id(users.getSetting_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}
