package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "additional_info_payment_id", insertable = false, updatable = false)
    private AdditionalInfoPayment additionalInfoPayment;

    @Column(name = "additional_info_payment_id")
    private Long additional_info_payment_id;

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

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public ZonedDateTime getDate_order() {
        return date_order;
    }

    public void setDate_order(ZonedDateTime date_order) {
        this.date_order = date_order;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public Integer getTotal_products() {
        return total_products;
    }

    public void setTotal_products(Integer total_products) {
        this.total_products = total_products;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getAdditional_info_payment_id() {
        return additional_info_payment_id;
    }

    public void setAdditional_info_payment_id(Long additional_info_payment_id) {
        this.additional_info_payment_id = additional_info_payment_id;
    }
} 