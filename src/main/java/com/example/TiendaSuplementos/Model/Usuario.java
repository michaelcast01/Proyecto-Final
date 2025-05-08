package com.example.TiendaSuplementos.Model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correo;
    @Column(name = "contraseña_hash", nullable = false)
    @JsonProperty("contraseñaHash")
    @JsonAlias("contrasenaHash")
    private String contrasenaHash;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String rol;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación", example = "2025-05-08T02:00:00Z")
    private Instant fechaCreacion;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Schema(description = "Pedidos realizados por el usuario")
    private List<Pedido> pedidos = new ArrayList<>();

    public Usuario() {}

    // ——— Getters y setters ———

    public Long getId() { return id; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Instant getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Instant fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos.clear();
        if (pedidos != null) {
            pedidos.forEach(p -> p.setUsuario(this));
            this.pedidos.addAll(pedidos);
        }
    }
}
