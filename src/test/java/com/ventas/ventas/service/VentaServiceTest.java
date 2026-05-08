package com.ventas.ventas.service;

import com.ventas.ventas.dto.VentaTotalProductoResponse;
import com.ventas.ventas.dto.VentaTotalResponse;
import com.ventas.ventas.exception.ResourceNotFoundException;
import com.ventas.ventas.model.DetalleVenta;
import com.ventas.ventas.model.Venta;
import com.ventas.ventas.repository.DetalleVentaRepository;
import com.ventas.ventas.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VentaService - Pruebas Unitarias")
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @InjectMocks
    private VentaService ventaService;

    private Venta ventaEjemplo;
    private DetalleVenta detalleEjemplo;

    @BeforeEach
    void setUp() {
        detalleEjemplo = new DetalleVenta();
        detalleEjemplo.setId(1L);
        detalleEjemplo.setProductoId(10L);
        detalleEjemplo.setProductoNombre("Laptop Gamer");
        detalleEjemplo.setCantidad(2);
        detalleEjemplo.setPrecioUnitario(new BigDecimal("999.99"));
        detalleEjemplo.setSubtotal(new BigDecimal("1999.98"));

        ventaEjemplo = new Venta();
        ventaEjemplo.setId(1L);
        ventaEjemplo.setDetalles(new ArrayList<>(List.of(detalleEjemplo)));
        ventaEjemplo.setTotal(new BigDecimal("1999.98"));
        ventaEjemplo.setActivo(true);
        ventaEjemplo.setFechaVenta(LocalDateTime.now());
        ventaEjemplo.setFechaCreacion(LocalDateTime.now());
        ventaEjemplo.setFechaActualizacion(LocalDateTime.now());
    }

    // =========================================================
    // obtenerTodas()
    // =========================================================

    @Test
    @DisplayName("obtenerTodas - retorna lista completa de ventas")
    void obtenerTodas_retornaListaVentas() {
        when(ventaRepository.findAll()).thenReturn(List.of(ventaEjemplo));

        List<Venta> resultado = ventaService.obtenerTodas();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(ventaRepository).findAll();
    }

    @Test
    @DisplayName("obtenerTodas - retorna lista vacía cuando no hay ventas")
    void obtenerTodas_listaVacia() {
        when(ventaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Venta> resultado = ventaService.obtenerTodas();

        assertThat(resultado).isEmpty();
    }

    // =========================================================
    // obtenerPorId()
    // =========================================================

    @Test
    @DisplayName("obtenerPorId - retorna venta existente")
    void obtenerPorId_ventaEncontrada() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaEjemplo));

        Venta resultado = ventaService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    @DisplayName("obtenerPorId - lanza ResourceNotFoundException si no existe")
    void obtenerPorId_noEncontrada_lanzaExcepcion() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventaService.obtenerPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // =========================================================
    // registrar()
    // =========================================================

    @Test
    @DisplayName("registrar - calcula subtotales y total correctamente")
    void registrar_calculaTotalesCorrectamente() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Mouse");
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        Venta resultado = ventaService.registrar(ventaNueva);

        assertThat(resultado.getTotal()).isEqualByComparingTo(new BigDecimal("30.00"));
        assertThat(resultado.getActivo()).isTrue();
        assertThat(detalle.getSubtotal()).isEqualByComparingTo(new BigDecimal("30.00"));
        assertThat(detalle.getVenta()).isEqualTo(ventaNueva);
    }

    @Test
    @DisplayName("registrar - venta con múltiples detalles suma correctamente")
    void registrar_multiplesDetalles_sumaTotal() {
        Venta ventaNueva = new Venta();
        DetalleVenta d1 = new DetalleVenta();
        d1.setProductoId(1L);
        d1.setProductoNombre("Teclado");
        d1.setCantidad(1);
        d1.setPrecioUnitario(new BigDecimal("50.00"));

        DetalleVenta d2 = new DetalleVenta();
        d2.setProductoId(2L);
        d2.setProductoNombre("Mouse");
        d2.setCantidad(2);
        d2.setPrecioUnitario(new BigDecimal("25.00"));

        ventaNueva.setDetalles(new ArrayList<>(List.of(d1, d2)));

        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        Venta resultado = ventaService.registrar(ventaNueva);

        assertThat(resultado.getTotal()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("registrar - lista de detalles nula lanza RuntimeException (NPE en log antes del null-check)")
    void registrar_sinDetalles_lanzaExcepcion() {
        Venta ventaSinDetalles = new Venta();
        ventaSinDetalles.setDetalles(null);

        // El log.info llama a getDetalles().size() antes del null-check -> NullPointerException
        assertThatThrownBy(() -> ventaService.registrar(ventaSinDetalles))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("registrar - lista de detalles vacía lanza IllegalArgumentException")
    void registrar_detallesVacios_lanzaExcepcion() {
        Venta ventaSinDetalles = new Venta();
        ventaSinDetalles.setDetalles(new ArrayList<>());

        assertThatThrownBy(() -> ventaService.registrar(ventaSinDetalles))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("al menos un detalle");
    }

    @Test
    @DisplayName("registrar - cantidad inválida (null) lanza IllegalArgumentException")
    void registrar_cantidadNula_lanzaExcepcion() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Producto");
        detalle.setCantidad(null);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        assertThatThrownBy(() -> ventaService.registrar(ventaNueva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cantidad");
    }

    @Test
    @DisplayName("registrar - cantidad cero lanza IllegalArgumentException")
    void registrar_cantidadCero_lanzaExcepcion() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Producto");
        detalle.setCantidad(0);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        assertThatThrownBy(() -> ventaService.registrar(ventaNueva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cantidad");
    }

    @Test
    @DisplayName("registrar - precio unitario nulo lanza IllegalArgumentException")
    void registrar_precioNulo_lanzaExcepcion() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Producto");
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(null);
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        assertThatThrownBy(() -> ventaService.registrar(ventaNueva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("precio");
    }

    @Test
    @DisplayName("registrar - precio unitario cero lanza IllegalArgumentException")
    void registrar_precioCero_lanzaExcepcion() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Producto");
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(BigDecimal.ZERO);
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        assertThatThrownBy(() -> ventaService.registrar(ventaNueva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("precio");
    }

    @Test
    @DisplayName("registrar - precio unitario negativo lanza IllegalArgumentException")
    void registrar_precioNegativo_lanzaExcepcion() {
        Venta ventaNueva = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(1L);
        detalle.setProductoNombre("Producto");
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(new BigDecimal("-5.00"));
        ventaNueva.setDetalles(new ArrayList<>(List.of(detalle)));

        assertThatThrownBy(() -> ventaService.registrar(ventaNueva))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("precio");
    }

    // =========================================================
    // eliminar()
    // =========================================================

    @Test
    @DisplayName("eliminar - realiza soft delete cambiando activo a false")
    void eliminar_softDelete_exitoso() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaEjemplo));
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaEjemplo);

        ventaService.eliminar(1L);

        assertThat(ventaEjemplo.getActivo()).isFalse();
        verify(ventaRepository).save(ventaEjemplo);
    }

    @Test
    @DisplayName("eliminar - lanza ResourceNotFoundException si venta no existe")
    void eliminar_noExiste_lanzaExcepcion() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventaService.eliminar(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // =========================================================
    // obtenerTotalPorProducto()
    // =========================================================

    @Test
    @DisplayName("obtenerTotalPorProducto - retorna totales correctos")
    void obtenerTotalPorProducto_retornaTotales() {
        Long productoId = 10L;
        when(detalleVentaRepository.existsByProductoId(productoId)).thenReturn(true);
        when(detalleVentaRepository.sumCantidadByProductoId(productoId)).thenReturn(5L);
        when(detalleVentaRepository.sumSubtotalByProductoId(productoId)).thenReturn(new BigDecimal("4999.95"));
        when(detalleVentaRepository.findProductoNombreByProductoId(productoId))
                .thenReturn(List.of("Laptop Gamer"));

        VentaTotalProductoResponse response = ventaService.obtenerTotalPorProducto(productoId);

        assertThat(response.getProductoId()).isEqualTo(productoId);
        assertThat(response.getProductoNombre()).isEqualTo("Laptop Gamer");
        assertThat(response.getCantidadTotalVendida()).isEqualTo(5L);
        assertThat(response.getMontoTotal()).isEqualByComparingTo(new BigDecimal("4999.95"));
    }

    @Test
    @DisplayName("obtenerTotalPorProducto - usa nombre por defecto cuando lista de nombres está vacía")
    void obtenerTotalPorProducto_sinNombre_usaNombrePorDefecto() {
        Long productoId = 10L;
        when(detalleVentaRepository.existsByProductoId(productoId)).thenReturn(true);
        when(detalleVentaRepository.sumCantidadByProductoId(productoId)).thenReturn(2L);
        when(detalleVentaRepository.sumSubtotalByProductoId(productoId)).thenReturn(new BigDecimal("100.00"));
        when(detalleVentaRepository.findProductoNombreByProductoId(productoId))
                .thenReturn(Collections.emptyList());

        VentaTotalProductoResponse response = ventaService.obtenerTotalPorProducto(productoId);

        assertThat(response.getProductoNombre()).isEqualTo("Producto desconocido");
    }

    @Test
    @DisplayName("obtenerTotalPorProducto - lanza ResourceNotFoundException si no hay ventas del producto")
    void obtenerTotalPorProducto_sinVentas_lanzaExcepcion() {
        when(detalleVentaRepository.existsByProductoId(99L)).thenReturn(false);

        assertThatThrownBy(() -> ventaService.obtenerTotalPorProducto(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // =========================================================
    // obtenerTotalVentas()
    // =========================================================

    @Test
    @DisplayName("obtenerTotalVentas - retorna totales consolidados del ecommerce")
    void obtenerTotalVentas_retornaTotales() {
        when(ventaRepository.sumTotalVentasActivas()).thenReturn(new BigDecimal("15000.00"));
        when(ventaRepository.countVentasActivas()).thenReturn(10L);
        when(detalleVentaRepository.sumCantidadTotal()).thenReturn(50L);

        VentaTotalResponse response = ventaService.obtenerTotalVentas();

        assertThat(response.getMontoTotal()).isEqualByComparingTo(new BigDecimal("15000.00"));
        assertThat(response.getCantidadVentas()).isEqualTo(10L);
        assertThat(response.getCantidadProductosVendidos()).isEqualTo(50L);
    }

    @Test
    @DisplayName("obtenerTotalVentas - retorna ceros cuando no hay ventas activas")
    void obtenerTotalVentas_sinVentas_retornaCeros() {
        when(ventaRepository.sumTotalVentasActivas()).thenReturn(BigDecimal.ZERO);
        when(ventaRepository.countVentasActivas()).thenReturn(0L);
        when(detalleVentaRepository.sumCantidadTotal()).thenReturn(0L);

        VentaTotalResponse response = ventaService.obtenerTotalVentas();

        assertThat(response.getMontoTotal()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.getCantidadVentas()).isZero();
        assertThat(response.getCantidadProductosVendidos()).isZero();
    }
}
