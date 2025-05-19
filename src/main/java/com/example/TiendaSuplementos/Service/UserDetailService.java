package com.example.TiendaSuplementos.Service;



import com.example.TiendaSuplementos.Model.UserDetail;
import com.example.TiendaSuplementos.Repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository repository;

    public UserDetailRepository getRepository() {
        return repository;
    }

    public void setRepository(UserDetailRepository repository) {
        this.repository = repository;
    }

    public List<UserDetail> findAll() {
        return repository.findAll();
    }


    public Optional<UserDetail> findById(Long id) {
        return repository.findById(id);
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
}
