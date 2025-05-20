package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.Model.Orders;
import com.example.TiendaSuplementos.Repository.OrdersRepository;
import com.example.TiendaSuplementos.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Orders> get() {
        return repository.findAll();
    }

    public Optional<Orders> getById(Long id) {
        return repository.findById(id);
    }

    public Orders save(Orders orders) {
        if (orders.getDate_order() == null) {
            orders.setDate_order(ZonedDateTime.now());
        }
        if (orders.getTotal_products() == null) {
            orders.setTotal_products(0);
        }
        if (orders.getTotal() == null) {
            orders.setTotal(0.0);
        }
        if (orders.getPayment_id() == null) {
            throw new RuntimeException("Payment ID is required");
        }
        if (orders.getUser_id() == null) {
            throw new RuntimeException("User ID is required");
        }
        if (!usersRepository.existsById(orders.getUser_id())) {
            throw new RuntimeException("User with ID " + orders.getUser_id() + " does not exist");
        }
        return repository.save(orders);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Orders update(Long id, Orders orders) {
        return repository.findById(id)
                .map(existing -> {
                    if (orders.getUser_id() != null) {
                        if (!usersRepository.existsById(orders.getUser_id())) {
                            throw new RuntimeException("User with ID " + orders.getUser_id() + " does not exist");
                        }
                        existing.setUser_id(orders.getUser_id());
                    }
                    if (orders.getDate_order() != null) {
                        existing.setDate_order(orders.getDate_order());
                    }
                    if (orders.getStatus_id() != null) {
                        existing.setStatus_id(orders.getStatus_id());
                    }
                    if (orders.getTotal_products() != null) {
                        existing.setTotal_products(orders.getTotal_products());
                    }
                    if (orders.getTotal() != null) {
                        existing.setTotal(orders.getTotal());
                    }
                    if (orders.getPayment_id() != null) {
                        existing.setPayment_id(orders.getPayment_id());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
} 