package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.UserDetail;
import com.example.TiendaSuplementos.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-details")

public class UserDetailController {
    @Autowired
    private UserDetailService service;

    @GetMapping
    public List<UserDetail> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetail> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{statusId}")
    public List<UserDetail> getByStatusId(@PathVariable Long statusId) {
        return service.findByStatusId(statusId);
    }
}
