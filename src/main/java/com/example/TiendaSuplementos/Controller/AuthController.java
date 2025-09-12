package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.DTO.AuthRequest;
import com.example.TiendaSuplementos.Model.Users;
import com.example.TiendaSuplementos.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<Users> userOptional = usersService.login(authRequest.getEmail(), authRequest.getPassword());
        
        if (userOptional.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", userOptional.get());
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
} 