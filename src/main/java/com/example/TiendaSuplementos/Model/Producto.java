package com.example.TiendaSuplementos.Model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del producto")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Descripción del producto")
    @Column(nullable = false)
    private String descripcion;

    @Schema(description = "Precio del producto")
    @Column(nullable = false)
    private Double precio;

    @Schema(description = "Cantidad disponible en stock")
    @Column(name = "cantidad_stock", nullable = false)
    private Integer cantidadStock;

    @Schema(description = "URL de la imagen del producto")
    @Column(name = "url_imagen")
    private String urlImagen;

    @Schema(description = "ID de la categoría existente")
    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    // Constructores
    public Producto() {}

    public Producto(String nombre, String descripcion, Double precio, Integer cantidadStock, String urlImagen, Long idCategoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.urlImagen = urlImagen;
        this.idCategoria = idCategoria;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }
    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
