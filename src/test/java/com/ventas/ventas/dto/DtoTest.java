package com.ventas.ventas.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTOs - Pruebas Unitarias")
class DtoTest {

    // =========================================================
    // VentaTotalResponse
    // =========================================================

    @Test
    @DisplayName("VentaTotalResponse - constructor con parámetros")
    void ventaTotalResponse_constructorConParametros() {
        VentaTotalResponse dto = new VentaTotalResponse(
                new BigDecimal("5000.00"), 10L, 30L);

        assertThat(dto.getMontoTotal()).isEqualByComparingTo(new BigDecimal("5000.00"));
        assertThat(dto.getCantidadVentas()).isEqualTo(10L);
        assertThat(dto.getCantidadProductosVendidos()).isEqualTo(30L);
    }

    @Test
    @DisplayName("VentaTotalResponse - constructor vacío y setters")
    void ventaTotalResponse_setters() {
        VentaTotalResponse dto = new VentaTotalResponse();
        dto.setMontoTotal(new BigDecimal("200.00"));
        dto.setCantidadVentas(2L);
        dto.setCantidadProductosVendidos(5L);

        assertThat(dto.getMontoTotal()).isEqualByComparingTo(new BigDecimal("200.00"));
        assertThat(dto.getCantidadVentas()).isEqualTo(2L);
        assertThat(dto.getCantidadProductosVendidos()).isEqualTo(5L);
    }

    // =========================================================
    // VentaTotalProductoResponse
    // =========================================================

    @Test
    @DisplayName("VentaTotalProductoResponse - constructor con parámetros")
    void ventaTotalProductoResponse_constructorConParametros() {
        VentaTotalProductoResponse dto = new VentaTotalProductoResponse(
                5L, "Laptop", 20L, new BigDecimal("19999.80"));

        assertThat(dto.getProductoId()).isEqualTo(5L);
        assertThat(dto.getProductoNombre()).isEqualTo("Laptop");
        assertThat(dto.getCantidadTotalVendida()).isEqualTo(20L);
        assertThat(dto.getMontoTotal()).isEqualByComparingTo(new BigDecimal("19999.80"));
    }

    @Test
    @DisplayName("VentaTotalProductoResponse - constructor vacío y setters")
    void ventaTotalProductoResponse_setters() {
        VentaTotalProductoResponse dto = new VentaTotalProductoResponse();
        dto.setProductoId(3L);
        dto.setProductoNombre("Monitor");
        dto.setCantidadTotalVendida(7L);
        dto.setMontoTotal(new BigDecimal("1400.00"));

        assertThat(dto.getProductoId()).isEqualTo(3L);
        assertThat(dto.getProductoNombre()).isEqualTo("Monitor");
        assertThat(dto.getCantidadTotalVendida()).isEqualTo(7L);
        assertThat(dto.getMontoTotal()).isEqualByComparingTo(new BigDecimal("1400.00"));
    }

    // =========================================================
    // ErrorDetails
    // =========================================================

    @Test
    @DisplayName("ErrorDetails - constructor con parámetros y getters")
    void errorDetails_constructorConParametros() {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        com.ventas.ventas.exception.ErrorDetails details =
                new com.ventas.ventas.exception.ErrorDetails(ahora, "Error", "uri=test");

        assertThat(details.getTimestamp()).isEqualTo(ahora);
        assertThat(details.getMessage()).isEqualTo("Error");
        assertThat(details.getDetails()).isEqualTo("uri=test");
    }

    @Test
    @DisplayName("ErrorDetails - constructor vacío y setters")
    void errorDetails_setters() {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        com.ventas.ventas.exception.ErrorDetails details = new com.ventas.ventas.exception.ErrorDetails();
        details.setTimestamp(ahora);
        details.setMessage("Mensaje");
        details.setDetails("Detalle");

        assertThat(details.getTimestamp()).isEqualTo(ahora);
        assertThat(details.getMessage()).isEqualTo("Mensaje");
        assertThat(details.getDetails()).isEqualTo("Detalle");
    }
}
