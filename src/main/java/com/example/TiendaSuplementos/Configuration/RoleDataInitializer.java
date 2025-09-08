package com.example.TiendaSuplementos.Configuration;

import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RolesRepository rolesRepository;

    public RoleDataInitializer(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // If no roles exist, create sensible defaults
        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            Roles admin = new Roles();
            admin.setName("ADMIN");
            rolesRepository.save(admin);

            Roles user = new Roles();
            user.setName("USER");
            rolesRepository.save(user);
        }
    }
}
