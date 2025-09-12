package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Orders order;

    @Column(name = "order_id", nullable = false)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Products product;

    @Column(name = "product_id", nullable = false)
    private Long product_id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    public Long getId() {
        return id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Orders getOrder() {
        return order;
    }

    public Products getProduct() {
        return product;
    }
} 