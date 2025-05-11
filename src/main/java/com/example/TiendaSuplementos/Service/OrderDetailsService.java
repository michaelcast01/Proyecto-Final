package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.OrderDetails;
import com.example.TiendaSuplementos.Repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository repository;

    public List<OrderDetails> get() {
        return repository.findAll();
    }

    public Optional<OrderDetails> getById(Long id) {
        return repository.findById(id);
    }

    //public List<OrderDetails> getByOrderId(Long order_id) {
       // return repository.findByOrderId(order_id);
    //}

    public OrderDetails save(OrderDetails orderDetails) {
        if (orderDetails.getQuantity() == null) {
            orderDetails.setQuantity(1);
        }
        return repository.save(orderDetails);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public OrderDetails update(Long id, OrderDetails orderDetails) {
        return repository.findById(id)
                .map(existing -> {
                    if (orderDetails.getOrder_id() != null) {
                        existing.setOrder_id(orderDetails.getOrder_id());
                    }
                    if (orderDetails.getProduct_id() != null) {
                        existing.setProduct_id(orderDetails.getProduct_id());
                    }
                    if (orderDetails.getQuantity() != null) {
                        existing.setQuantity(orderDetails.getQuantity());
                    }
                    if (orderDetails.getPrice() != null) {
                        existing.setPrice(orderDetails.getPrice());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
    }
} 