package com.ventas.ventas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DetalleVenta - Pruebas de Modelo")
class DetalleVentaTest {

    @Test
    @DisplayName("constructor con parámetros calcula subtotal automáticamente")
    void constructorConParametros_calculaSubtotal() {
        DetalleVenta detalle = new DetalleVenta(
                1L, "Mouse", 3, new BigDecimal("15.00"));

        assertThat(detalle.getProductoId()).isEqualTo(1L);
        assertThat(detalle.getProductoNombre()).isEqualTo("Mouse");
        assertThat(detalle.getCantidad()).isEqualTo(3);
        assertThat(detalle.getPrecioUnitario()).isEqualByComparingTo(new BigDecimal("15.00"));
        assertThat(detalle.getSubtotal()).isEqualByComparingTo(new BigDecimal("45.00"));
    }

    @Test
    @DisplayName("getters y setters funcionan correctamente")
    void gettersSetters_funcionan() {
        DetalleVenta detalle = new DetalleVenta();
        Venta venta = new Venta();

        detalle.setId(1L);
        detalle.setVenta(venta);
        detalle.setProductoId(5L);
        detalle.setProductoNombre("Teclado");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("49.99"));
        detalle.setSubtotal(new BigDecimal("99.98"));

        assertThat(detalle.getId()).isEqualTo(1L);
        assertThat(detalle.getVenta()).isEqualTo(venta);
        assertThat(detalle.getProductoId()).isEqualTo(5L);
        assertThat(detalle.getProductoNombre()).isEqualTo("Teclado");
        assertThat(detalle.getCantidad()).isEqualTo(2);
        assertThat(detalle.getPrecioUnitario()).isEqualByComparingTo(new BigDecimal("49.99"));
        assertThat(detalle.getSubtotal()).isEqualByComparingTo(new BigDecimal("99.98"));
    }
}
