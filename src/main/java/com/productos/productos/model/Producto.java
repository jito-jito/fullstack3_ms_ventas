package com.productos.productos.model;

// ===============================
// IMPORTACIONES JPA
// ===============================
import jakarta.persistence.*;

// ===============================
// IMPORTACIONES DE VALIDACIÓN
// ===============================
import jakarta.validation.constraints.*;

// ===============================
// IMPORTACIONES DE TIEMPO
// ===============================
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ===============================
 * MODELO: Producto
 * ===============================
 * 
 * Entidad que representa un producto del sistema.
 * Permite gestionar productos con operaciones CRUD básicas.
 */
@Entity
@Table(name = "PRODUCTOS")
public class Producto {

    /**
     * Clave primaria de la tabla productos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción detallada del producto
     */
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    /**
     * Precio del producto
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "PRECIO", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    /**
     * Categoría del producto
     */
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(name = "CATEGORIA", nullable = false, length = 50)
    private String categoria;

    /**
     * Cantidad en stock del producto
     */
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    /**
     * Estado activo/inactivo del producto
     */
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = true;

    /**
     * Fecha de creación del producto
     */
    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Fecha de última actualización
     */
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío obligatorio para JPA
     */
    public Producto() {
    }

    /**
     * Constructor con parámetros principales
     */
    public Producto(String nombre, String descripcion, BigDecimal precio, String categoria, Integer stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Se ejecuta antes de persistir la entidad
     */
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Se ejecuta antes de actualizar la entidad
     */
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // ===============================
    // GETTERS Y SETTERS
    // ===============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", stock=" + stock +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}