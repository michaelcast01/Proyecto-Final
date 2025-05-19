package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "settings_payments")
public class SettingsPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "settings_id", insertable = false, updatable = false)
    private SettingsDetail settings;

    @Column(name = "settings_id")
    private Long settings_id;

    @ManyToOne
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payments payment;

    @Column(name = "payment_id")
    private Long payment_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSettings_id() {
        return settings_id;
    }

    public void setSettings_id(Long settings_id) {
        this.settings_id = settings_id;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
    }
} 