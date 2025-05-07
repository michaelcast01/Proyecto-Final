package com.example.TiendaSuplementos.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "pedidos")
// Ignora el campo "usuario" al deserializar JSON (pero lo sigue enviando al serializar)
@JsonIgnoreProperties(value = { "usuario" }, allowGetters = true)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference  // rompe la referencia cíclica con Usuario
    private Usuario usuario;

    @Column(name = "fecha_pedido")
    private Timestamp fechaPedido;

    private String estado;
    private Double montoTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalle;

    // ——— Getters ———

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public List<DetallePedido> getDetalle() {
        return detalle;
    }

    // ——— Setters ———

    // Público para que UsuarioService pueda hacer p.setUsuario(usuario) sin problema.
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setDetalle(List<DetallePedido> detalle) {
        this.detalle = detalle;
    }
}