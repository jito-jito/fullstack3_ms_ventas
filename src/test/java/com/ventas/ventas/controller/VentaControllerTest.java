package com.ventas.ventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.ventas.dto.VentaTotalProductoResponse;
import com.ventas.ventas.dto.VentaTotalResponse;
import com.ventas.ventas.exception.GlobalExceptionHandler;
import com.ventas.ventas.exception.ResourceNotFoundException;
import com.ventas.ventas.model.DetalleVenta;
import com.ventas.ventas.model.Venta;
import com.ventas.ventas.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VentaController - Pruebas Unitarias")
class VentaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules();

    private Venta ventaEjemplo;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(ventaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        detalle.setProductoId(10L);
        detalle.setProductoNombre("Laptop Gamer");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("999.99"));
        detalle.setSubtotal(new BigDecimal("1999.98"));

        ventaEjemplo = new Venta();
        ventaEjemplo.setId(1L);
        ventaEjemplo.setDetalles(new ArrayList<>(List.of(detalle)));
        ventaEjemplo.setTotal(new BigDecimal("1999.98"));
        ventaEjemplo.setActivo(true);
        ventaEjemplo.setFechaVenta(LocalDateTime.now());
        ventaEjemplo.setFechaCreacion(LocalDateTime.now());
        ventaEjemplo.setFechaActualizacion(LocalDateTime.now());
    }

    // =========================================================
    // POST /api/ventas
    // =========================================================

    @Test
    @DisplayName("POST /api/ventas - registra venta y retorna 201")
    void registrarVenta_exitoso_retorna201() throws Exception {
        when(ventaService.registrar(any(Venta.class))).thenReturn(ventaEjemplo);

        String body = """
                {
                  "detalles": [
                    {
                      "productoId": 10,
                      "productoNombre": "Laptop Gamer",
                      "cantidad": 2,
                      "precioUnitario": 999.99
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    @DisplayName("POST /api/ventas - sin detalles retorna 400 por @Valid")
    void registrarVenta_sinDetalles_retorna400() throws Exception {
        String body = """
                {
                  "detalles": []
                }
                """;

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/ventas - IllegalArgumentException retorna 400")
    void registrarVenta_illegalArgument_retorna400() throws Exception {
        when(ventaService.registrar(any(Venta.class)))
                .thenThrow(new IllegalArgumentException("La venta debe tener al menos un detalle"));

        String body = """
                {
                  "detalles": [
                    {
                      "productoId": 1,
                      "productoNombre": "Producto",
                      "cantidad": 1,
                      "precioUnitario": 10.00
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // GET /api/ventas
    // =========================================================

    @Test
    @DisplayName("GET /api/ventas - retorna lista de ventas con 200")
    void obtenerTodas_retornaLista200() throws Exception {
        when(ventaService.obtenerTodas()).thenReturn(List.of(ventaEjemplo));

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/ventas - retorna lista vacía con 200")
    void obtenerTodas_listaVacia_retorna200() throws Exception {
        when(ventaService.obtenerTodas()).thenReturn(List.of());

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // =========================================================
    // GET /api/ventas/{id}
    // =========================================================

    @Test
    @DisplayName("GET /api/ventas/{id} - retorna venta existente con 200")
    void obtenerPorId_encontrada_retorna200() throws Exception {
        when(ventaService.obtenerPorId(1L)).thenReturn(ventaEjemplo);

        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    @DisplayName("GET /api/ventas/{id} - venta no encontrada retorna 404")
    void obtenerPorId_noEncontrada_retorna404() throws Exception {
        when(ventaService.obtenerPorId(99L))
                .thenThrow(new ResourceNotFoundException("Venta no encontrada con ID: 99"));

        mockMvc.perform(get("/api/ventas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Venta no encontrada con ID: 99"));
    }

    // =========================================================
    // DELETE /api/ventas/{id}
    // =========================================================

    @Test
    @DisplayName("DELETE /api/ventas/{id} - elimina venta y retorna 204")
    void eliminarVenta_exitoso_retorna204() throws Exception {
        doNothing().when(ventaService).eliminar(1L);

        mockMvc.perform(delete("/api/ventas/1"))
                .andExpect(status().isNoContent());

        verify(ventaService).eliminar(1L);
    }

    @Test
    @DisplayName("DELETE /api/ventas/{id} - venta no encontrada retorna 404")
    void eliminarVenta_noEncontrada_retorna404() throws Exception {
        doThrow(new ResourceNotFoundException("Venta no encontrada con ID: 99"))
                .when(ventaService).eliminar(99L);

        mockMvc.perform(delete("/api/ventas/99"))
                .andExpect(status().isNotFound());
    }

    // =========================================================
    // GET /api/ventas/total
    // =========================================================

    @Test
    @DisplayName("GET /api/ventas/total - retorna totales del ecommerce con 200")
    void obtenerTotalVentas_retorna200() throws Exception {
        VentaTotalResponse totalResponse = new VentaTotalResponse(
                new BigDecimal("15000.00"), 10L, 50L);
        when(ventaService.obtenerTotalVentas()).thenReturn(totalResponse);

        mockMvc.perform(get("/api/ventas/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoTotal").value(15000.00))
                .andExpect(jsonPath("$.cantidadVentas").value(10))
                .andExpect(jsonPath("$.cantidadProductosVendidos").value(50));
    }

    // =========================================================
    // GET /api/ventas/producto/{productoId}/total
    // =========================================================

    @Test
    @DisplayName("GET /api/ventas/producto/{id}/total - retorna totales del producto con 200")
    void obtenerTotalPorProducto_retorna200() throws Exception {
        VentaTotalProductoResponse productoResponse = new VentaTotalProductoResponse(
                10L, "Laptop Gamer", 5L, new BigDecimal("4999.95"));
        when(ventaService.obtenerTotalPorProducto(10L)).thenReturn(productoResponse);

        mockMvc.perform(get("/api/ventas/producto/10/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(10))
                .andExpect(jsonPath("$.productoNombre").value("Laptop Gamer"))
                .andExpect(jsonPath("$.cantidadTotalVendida").value(5));
    }

    @Test
    @DisplayName("GET /api/ventas/producto/{id}/total - producto sin ventas retorna 404")
    void obtenerTotalPorProducto_sinVentas_retorna404() throws Exception {
        when(ventaService.obtenerTotalPorProducto(99L))
                .thenThrow(new ResourceNotFoundException("No se encontraron ventas para el producto con ID: 99"));

        mockMvc.perform(get("/api/ventas/producto/99/total"))
                .andExpect(status().isNotFound());
    }
}
