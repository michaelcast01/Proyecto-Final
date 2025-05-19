package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "date_order", nullable = false)
    private ZonedDateTime date_order;

    @ManyToOne
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private Statuses status;

    @Column(name = "status_id")
    private Long status_id;

    @Column(name = "total_products", nullable = false)
    private Integer total_products;

    @Column(name = "total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "order")
    private Set<OrderProductDetail> orderProducts;

    public Long getOrder_id() {
        return order_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public ZonedDateTime getDate_order() {
        return date_order;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public Integer getTotal_products() {
        return total_products;
    }

    public Double getTotal() {
        return total;
    }

    public Users getUser() {
        return user;
    }

    public Statuses getStatus() {
        return status;
    }

    public Set<OrderProductDetail> getOrderProducts() {
        return orderProducts;
    }
} 