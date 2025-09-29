package com.example.TiendaSuplementos.DTO;

import com.example.TiendaSuplementos.Model.Products;

public class OrderProductDetailDTO {
    private Long id;
    private Products product;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    private Long orderId;

    // Constructor vacío
    public OrderProductDetailDTO() {
    }

    // Constructor completo
    public OrderProductDetailDTO(Long id, Products product, Integer quantity, Double unitPrice, Long orderId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.subtotal = (quantity != null && unitPrice != null) ? quantity * unitPrice : 0.0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        // Recalcular subtotal cuando cambia la cantidad
        this.subtotal = (quantity != null && unitPrice != null) ? quantity * unitPrice : 0.0;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        // Recalcular subtotal cuando cambia el precio unitario
        this.subtotal = (quantity != null && unitPrice != null) ? quantity * unitPrice : 0.0;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderProductDetailDTO{" +
                "id=" + id +
                ", product=" + (product != null ? product.getName() : "null") +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                ", orderId=" + orderId +
                '}';
    }
}