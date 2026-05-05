package com.ventas.ventas.dto;

import java.math.BigDecimal;

/**
 * ===============================
 * DTO: VentaTotalProductoResponse
 * ===============================
 *
 * Modelo de respuesta para representar las ventas
 * totales de un producto específico.
 */
public class VentaTotalProductoResponse {

    private Long productoId;
    private String productoNombre;
    private Long cantidadTotalVendida;
    private BigDecimal montoTotal;

    /**
     * Constructor con todos los parámetros
     */
    public VentaTotalProductoResponse(Long productoId, String productoNombre, Long cantidadTotalVendida, BigDecimal montoTotal) {
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.cantidadTotalVendida = cantidadTotalVendida;
        this.montoTotal = montoTotal;
    }

    /**
     * Constructor vacío
     */
    public VentaTotalProductoResponse() {
    }

    // ===============================
    // GETTERS Y SETTERS
    // ===============================

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

    public Long getCantidadTotalVendida() {
        return cantidadTotalVendida;
    }

    public void setCantidadTotalVendida(Long cantidadTotalVendida) {
        this.cantidadTotalVendida = cantidadTotalVendida;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
}
