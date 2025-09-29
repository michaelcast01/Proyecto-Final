package com.example.TiendaSuplementos.Service;

import com.example.TiendaSuplementos.DTO.OrderProductDetailDTO;
import com.example.TiendaSuplementos.Model.OrderProduct;
import com.example.TiendaSuplementos.Model.Products;
import com.example.TiendaSuplementos.Repository.OrderProductRepository;
import com.example.TiendaSuplementos.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderProductService {

    @Autowired
    private OrderProductRepository repository;

    @Autowired
    private ProductsRepository productsRepository;

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

        if (orderProduct.getProduct_id() == null) {
            throw new RuntimeException("Product ID es requerido");
        }

        Products product = productsRepository.findById(orderProduct.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Producto con ID " + orderProduct.getProduct_id() + " no existe"));

        Integer stock = product.getStock() == null ? 0 : product.getStock();
        if (stock < orderProduct.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para el producto ID " + orderProduct.getProduct_id());
        }

        product.setStock(stock - orderProduct.getQuantity());
        productsRepository.save(product);

        return repository.save(orderProduct);
    }

    public void delete(Long id) {
        Optional<OrderProduct> existingOpt = repository.findById(id);
        if (existingOpt.isPresent()) {
            OrderProduct existing = existingOpt.get();
            Products product = productsRepository.findById(existing.getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + existing.getProduct_id() + " no existe"));
            Integer stock = product.getStock() == null ? 0 : product.getStock();
            product.setStock(stock + (existing.getQuantity() == null ? 0 : existing.getQuantity()));
            productsRepository.save(product);
        }
        repository.deleteById(id);
    }

    public OrderProduct update(Long id, OrderProduct orderProduct) {
        return repository.findById(id)
                .map(existing -> {
                    if (orderProduct.getOrder_id() != null) {
                        existing.setOrder_id(orderProduct.getOrder_id());
                    }
                    if (orderProduct.getProduct_id() != null) {
                        // if product changed (or was previously null), restore stock of old product and deduct from new product
                        if (existing.getProduct_id() == null || !orderProduct.getProduct_id().equals(existing.getProduct_id())) {
                            if (existing.getProduct_id() != null) {
                                Products oldProduct = productsRepository.findById(existing.getProduct_id())
                                        .orElseThrow(() -> new RuntimeException("Producto con ID " + existing.getProduct_id() + " no existe"));
                                Integer oldStock = oldProduct.getStock() == null ? 0 : oldProduct.getStock();
                                oldProduct.setStock(oldStock + (existing.getQuantity() == null ? 0 : existing.getQuantity()));
                                productsRepository.save(oldProduct);
                            }

                            Products newProduct = productsRepository.findById(orderProduct.getProduct_id())
                                    .orElseThrow(() -> new RuntimeException("Producto con ID " + orderProduct.getProduct_id() + " no existe"));
                            Integer newStock = newProduct.getStock() == null ? 0 : newProduct.getStock();
                            Integer qty = orderProduct.getQuantity() == null ? existing.getQuantity() : orderProduct.getQuantity();
                            if (qty == null) qty = 0;
                            if (newStock < qty) {
                                throw new RuntimeException("Stock insuficiente para el producto ID " + orderProduct.getProduct_id());
                            }
                            newProduct.setStock(newStock - qty);
                            productsRepository.save(newProduct);

                            existing.setProduct_id(orderProduct.getProduct_id());
                        }
                    }
                    if (orderProduct.getQuantity() != null) {
                        int oldQty = existing.getQuantity() == null ? 0 : existing.getQuantity();
                        int newQty = orderProduct.getQuantity();
                        int delta = newQty - oldQty; // positive means we need to reduce more stock
                        if (delta != 0) {
                            Products product = productsRepository.findById(existing.getProduct_id())
                                    .orElseThrow(() -> new RuntimeException("Producto con ID " + existing.getProduct_id() + " no existe"));
                            Integer stock = product.getStock() == null ? 0 : product.getStock();
                            if (delta > 0) {
                                if (stock < delta) {
                                    throw new RuntimeException("Stock insuficiente para el producto ID " + existing.getProduct_id());
                                }
                                product.setStock(stock - delta);
                            } else {
                                product.setStock(stock + (-delta));
                            }
                            productsRepository.save(product);
                        }
                        existing.setQuantity(orderProduct.getQuantity());
                    }
                    if (orderProduct.getPrice() != null) {
                        existing.setPrice(orderProduct.getPrice());
                    }
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
    }

    /**
     * Obtiene los detalles de productos de una orden con cantidades reales
     * @param orderId ID de la orden
     * @return Lista de OrderProductDetailDTO con información completa del producto y cantidades
     */
    public List<OrderProductDetailDTO> getOrderProductDetailsByOrderId(Long orderId) {
        List<OrderProduct> orderProducts = repository.findByOrderId(orderId);
        
        return orderProducts.stream()
                .map(orderProduct -> {
                    // Obtener información completa del producto
                    Products product = productsRepository.findById(orderProduct.getProduct_id())
                            .orElseThrow(() -> new RuntimeException("Producto con ID " + orderProduct.getProduct_id() + " no existe"));
                    
                    // Crear el DTO con la información combinada
                    return new OrderProductDetailDTO(
                            orderProduct.getId(),
                            product,
                            orderProduct.getQuantity(), // CANTIDAD REAL COMPRADA
                            orderProduct.getPrice(),    // PRECIO UNITARIO
                            orderProduct.getOrder_id()
                    );
                })
                .collect(Collectors.toList());
    }
}