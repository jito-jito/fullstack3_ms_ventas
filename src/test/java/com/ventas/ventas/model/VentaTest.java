package com.ventas.ventas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Venta - Pruebas de Modelo")
class VentaTest {

    @Test
    @DisplayName("getters y setters funcionan correctamente")
    void gettersSetters_funcionan() {
        Venta venta = new Venta();
        LocalDateTime ahora = LocalDateTime.now();
        List<DetalleVenta> detalles = new ArrayList<>();

        venta.setId(1L);
        venta.setDetalles(detalles);
        venta.setTotal(new BigDecimal("500.00"));
        venta.setActivo(true);
        venta.setFechaVenta(ahora);
        venta.setFechaCreacion(ahora);
        venta.setFechaActualizacion(ahora);

        assertThat(venta.getId()).isEqualTo(1L);
        assertThat(venta.getDetalles()).isEqualTo(detalles);
        assertThat(venta.getTotal()).isEqualByComparingTo(new BigDecimal("500.00"));
        assertThat(venta.getActivo()).isTrue();
        assertThat(venta.getFechaVenta()).isEqualTo(ahora);
        assertThat(venta.getFechaCreacion()).isEqualTo(ahora);
        assertThat(venta.getFechaActualizacion()).isEqualTo(ahora);
    }

    @Test
    @DisplayName("toString incluye campos principales")
    void toString_incluyeCamposPrincipales() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setTotal(new BigDecimal("100.00"));
        venta.setActivo(true);

        String resultado = venta.toString();

        assertThat(resultado).contains("1");
        assertThat(resultado).contains("100.00");
    }

    @Test
    @DisplayName("activo es true por defecto")
    void activoEsTruePorDefecto() {
        Venta venta = new Venta();
        assertThat(venta.getActivo()).isTrue();
    }

    @Test
    @DisplayName("lista de detalles inicializada vacía por defecto")
    void detallesIniciadosVaciosPorDefecto() {
        Venta venta = new Venta();
        assertThat(venta.getDetalles()).isNotNull().isEmpty();
    }
}
