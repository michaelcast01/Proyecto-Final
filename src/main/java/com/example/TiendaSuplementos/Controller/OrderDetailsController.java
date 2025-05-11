package com.example.TiendaSuplementos.Controller;

import com.example.TiendaSuplementos.Model.OrderDetails;
import com.example.TiendaSuplementos.Service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService service;

    @GetMapping
    public List<OrderDetails> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@GetMapping("/order/{order_id}")
    //public ResponseEntity<List<OrderDetails>> getByOrderId(@PathVariable Long order_id) {
       // return service.getByOrderId(order_id)
          //      .map(ResponseEntity::ok)
         //       .orElse(ResponseEntity.notFound().build());
  //  }

    @PostMapping
    public ResponseEntity<OrderDetails> save(@RequestBody OrderDetails orderDetails) {
        OrderDetails data = service.save(orderDetails);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> update(@PathVariable Long id, @RequestBody OrderDetails orderDetails) {
        OrderDetails data = service.update(id, orderDetails);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 