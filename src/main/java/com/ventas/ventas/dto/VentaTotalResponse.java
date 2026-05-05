package com.ventas.ventas.dto;

import java.math.BigDecimal;

/**
 * ===============================
 * DTO: VentaTotalResponse
 * ===============================
 *
 * Modelo de respuesta para representar las ventas
 * totales del ecommerce completo.
 */
public class VentaTotalResponse {

    private BigDecimal montoTotal;
    private Long cantidadVentas;
    private Long cantidadProductosVendidos;

    /**
     * Constructor con todos los parámetros
     */
    public VentaTotalResponse(BigDecimal montoTotal, Long cantidadVentas, Long cantidadProductosVendidos) {
        this.montoTotal = montoTotal;
        this.cantidadVentas = cantidadVentas;
        this.cantidadProductosVendidos = cantidadProductosVendidos;
    }

    /**
     * Constructor vacío
     */
    public VentaTotalResponse() {
    }

    // ===============================
    // GETTERS Y SETTERS
    // ===============================

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(Long cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public Long getCantidadProductosVendidos() {
        return cantidadProductosVendidos;
    }

    public void setCantidadProductosVendidos(Long cantidadProductosVendidos) {
        this.cantidadProductosVendidos = cantidadProductosVendidos;
    }
}
