// Pedido.java
package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "fecha_pedido")
    private Timestamp fechaPedido;

    private String estado;

    @Column(name = "monto_total")
    private Double montoTotal;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<DetallePedido> detalle = new ArrayList<>();

    // --- Constructores ---
    public Pedido() {}

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Timestamp getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public List<DetallePedido> getDetalle() { return detalle; }
    public void setDetalle(List<DetallePedido> detalle) {
        this.detalle.clear();
        if (detalle != null) {
            detalle.forEach(this::addDetalle);
        }
    }

    public void addDetalle(DetallePedido d) {
        d.setPedido(this);
        this.detalle.add(d);
    }

    public void removeDetalle(DetallePedido d) {
        d.setPedido(null);
        this.detalle.remove(d);
    }
}
