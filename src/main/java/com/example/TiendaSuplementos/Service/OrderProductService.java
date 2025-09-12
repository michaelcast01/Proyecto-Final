package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.OrderProduct;
import com.example.TiendaSuplementos.Repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository repository;

    public List<OrderProduct> get() {
        return repository.findAll();
    }

    public Optional<OrderProduct> getById(Long id) {
        return repository.findById(id);
    }

    public OrderProduct save(OrderProduct orderProduct) {
        if (orderProduct.getQuantity() == null) {
            orderProduct.setQuantity(1);
        }
        return repository.save(orderProduct);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public OrderProduct update(Long id, OrderProduct orderProduct) {
        return repository.findById(id)
                .map(existing -> {
                    if (orderProduct.getOrder_id() != null) {
                        existing.setOrder_id(orderProduct.getOrder_id());
                    }
                    if (orderProduct.getProduct_id() != null) {
                        existing.setProduct_id(orderProduct.getProduct_id());
                    }
                    if (orderProduct.getQuantity() != null) {
                        existing.setQuantity(orderProduct.getQuantity());
                    }
                    if (orderProduct.getPrice() != null) {
                        existing.setPrice(orderProduct.getPrice());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
    }
} 