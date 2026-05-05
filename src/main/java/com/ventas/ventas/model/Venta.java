package com.ventas.ventas.model;

// ===============================
// IMPORTACIONES JPA
// ===============================
import jakarta.persistence.*;

// ===============================
// IMPORTACIONES DE VALIDACIÓN
// ===============================
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

// ===============================
// IMPORTACIONES DE JACKSON
// ===============================
import com.fasterxml.jackson.annotation.JsonManagedReference;

// ===============================
// IMPORTACIONES DE JAVA
// ===============================
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ===============================
 * MODELO: Venta
 * ===============================
 * 
 * Entidad que representa una venta del sistema.
 * Una venta contiene uno o más detalles (productos vendidos).
 * Permite gestionar ventas con operaciones CRUD básicas.
 */
@Entity
@Table(name = "VENTAS")
public class Venta {

    /**
     * Clave primaria de la tabla ventas
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Lista de detalles (productos) de la venta
     */
    @Valid
    @NotEmpty(message = "La venta debe tener al menos un detalle")
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleVenta> detalles = new ArrayList<>();

    /**
     * Total de la venta (calculado automáticamente)
     */
    @Column(name = "TOTAL", nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    /**
     * Estado activo/inactivo de la venta
     */
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = true;

    /**
     * Fecha en que se realizó la venta
     */
    @Column(name = "FECHA_VENTA", nullable = false)
    private LocalDateTime fechaVenta;

    /**
     * Fecha de creación del registro
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
    public Venta() {
    }

    /**
     * Se ejecuta antes de persistir la entidad
     */
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (fechaVenta == null) {
            fechaVenta = LocalDateTime.now();
        }
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

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
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
        return "Venta{" +
                "id=" + id +
                ", total=" + total +
                ", activo=" + activo +
                ", fechaVenta=" + fechaVenta +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", detalles=" + detalles.size() +
                '}';
    }
}
