package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.DTO.AuthRequest;
import com.example.TiendaSuplementos.Model.Users;
import com.example.TiendaSuplementos.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Verificar si el usuario existe
        Users user = usersService.findByEmail(authRequest.getEmail());
        
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Email not found");
            return ResponseEntity.status(404).body(response);
        }
        
        // Verificar si el usuario está deshabilitado
        if (!user.getEnabled()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account is disabled");
            return ResponseEntity.status(423).body(response);
        }
        
        // Verificar contraseña
        if (!user.getPassword().equals(authRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Incorrect password");
            return ResponseEntity.status(401).body(response);
        }
        
        // Login exitoso
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", user);
        return ResponseEntity.ok(response);
    }
} 