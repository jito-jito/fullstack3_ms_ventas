package com.ventas.ventas.model;

// ===============================
// IMPORTACIONES JPA
// ===============================
import jakarta.persistence.*;

// ===============================
// IMPORTACIONES DE VALIDACIÓN
// ===============================
import jakarta.validation.constraints.*;

// ===============================
// IMPORTACIONES DE JACKSON
// ===============================
import com.fasterxml.jackson.annotation.JsonBackReference;

// ===============================
// IMPORTACIONES DE JAVA
// ===============================
import java.math.BigDecimal;

/**
 * ===============================
 * MODELO: DetalleVenta
 * ===============================
 * 
 * Entidad que representa un detalle (línea) de una venta.
 * Cada detalle corresponde a un producto vendido con su
 * cantidad, precio unitario y subtotal calculado.
 */
@Entity
@Table(name = "DETALLE_VENTAS")
public class DetalleVenta {

    /**
     * Clave primaria del detalle de venta
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Referencia a la venta padre
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENTA_ID", nullable = false)
    @JsonBackReference
    private Venta venta;

    /**
     * ID del producto vendido (referencia al microservicio de productos)
     */
    @NotNull(message = "El ID del producto es obligatorio")
    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;

    /**
     * Nombre del producto al momento de la venta (desnormalizado)
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre del producto no puede exceder 100 caracteres")
    @Column(name = "PRODUCTO_NOMBRE", nullable = false, length = 100)
    private String productoNombre;

    /**
     * Cantidad de unidades vendidas
     */
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    /**
     * Precio unitario del producto al momento de la venta
     */
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(name = "PRECIO_UNITARIO", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    /**
     * Subtotal del detalle (cantidad * precioUnitario) - calculado automáticamente
     */
    @Column(name = "SUBTOTAL", nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    /**
     * Constructor vacío obligatorio para JPA
     */
    public DetalleVenta() {
    }

    /**
     * Constructor con parámetros principales
     */
    public DetalleVenta(Long productoId, String productoNombre, Integer cantidad, BigDecimal precioUnitario) {
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", productoNombre='" + productoNombre + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
