package com.example.TiendaSuplementos.Controller;
import com.example.TiendaSuplementos.Model.Roles;
import com.example.TiendaSuplementos.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService service;

    @PostMapping
    public ResponseEntity<Roles> crear(@RequestBody Roles roles) {
        Roles creada = service.guardar(roles);
        return ResponseEntity.ok(creada);
    }

}