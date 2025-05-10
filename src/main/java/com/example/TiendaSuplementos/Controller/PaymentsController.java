package com.example.TiendaSuplementos.Controller;
import com.example.TiendaSuplementos.Model.Payments;
import com.example.TiendaSuplementos.Service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService service;

    @PostMapping
    public ResponseEntity<Payments> crear(@RequestBody Payments payments) {
        Payments creada = service.guardar(payments);
        return ResponseEntity.ok(creada);
    }
}
