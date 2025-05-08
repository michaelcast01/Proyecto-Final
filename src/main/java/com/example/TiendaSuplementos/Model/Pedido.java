package com.example.TiendaSuplementos.Model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de pedido", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "fecha_pedido", nullable = false)
    @Schema(description = "Fecha del pedido", example = "2025-05-08T02:00:00Z")
    private Instant fechaPedido;

    @Column(nullable = false)
    @Schema(description = "Estado del pedido", example = "PENDIENTE")
    private String estado;

    @Column(name = "monto_total", nullable = false)
    @Schema(description = "Monto total del pedido")
    private Double montoTotal;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Schema(description = "Detalle de productos en el pedido")
    private List<DetallePedido> detalle = new ArrayList<>();

    public Pedido() {}

    // ——— Getters y setters ———

    public Long getId() { return id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Instant getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Instant fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public List<DetallePedido> getDetalle() { return detalle; }
    public void setDetalle(List<DetallePedido> detalle) {
        this.detalle.clear();
        if (detalle != null) {
            detalle.forEach(d -> d.setPedido(this));
            this.detalle.addAll(detalle);
        }
    }
}
