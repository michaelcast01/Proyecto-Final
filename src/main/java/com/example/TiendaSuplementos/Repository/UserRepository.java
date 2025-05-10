package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
