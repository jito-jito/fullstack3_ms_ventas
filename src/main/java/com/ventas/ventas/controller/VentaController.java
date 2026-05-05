package com.ventas.ventas.controller;

// ===============================
// IMPORTACIONES SPRING
// ===============================
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ventas.ventas.dto.VentaTotalProductoResponse;
import com.ventas.ventas.dto.VentaTotalResponse;
import com.ventas.ventas.model.Venta;
import com.ventas.ventas.service.VentaService;

// ===============================
// VALIDACIONES
// ===============================
import jakarta.validation.Valid;

import java.util.List;

/**
 * ===============================
 * CONTROLLER: VentaController
 * ===============================
 *
 * Maneja las solicitudes HTTP del cliente para gestión de ventas.
 * Endpoints habilitados: registrar, listar, buscar por ID,
 * eliminar, totales por producto y totales del ecommerce.
 */
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // ==========================================================
    // REGISTRAR NUEVA VENTA
    // ==========================================================

    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@Valid @RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.registrar(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    // ==========================================================
    // OBTENER TODAS LAS VENTAS
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodas();
        return ResponseEntity.ok(ventas);
    }

    // ==========================================================
    // OBTENER VENTA POR ID
    // ==========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        Venta venta = ventaService.obtenerPorId(id);
        return ResponseEntity.ok(venta);
    }

    // ==========================================================
    // ELIMINAR VENTA (SOFT DELETE - cambiar a inactivo)
    // ==========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // OBTENER VENTAS TOTALES POR PRODUCTO
    // ==========================================================

    @GetMapping("/producto/{productoId}/total")
    public ResponseEntity<VentaTotalProductoResponse> obtenerTotalPorProducto(@PathVariable Long productoId) {
        VentaTotalProductoResponse totalProducto = ventaService.obtenerTotalPorProducto(productoId);
        return ResponseEntity.ok(totalProducto);
    }

    // ==========================================================
    // OBTENER VENTAS TOTALES DEL ECOMMERCE
    // ==========================================================

    @GetMapping("/total")
    public ResponseEntity<VentaTotalResponse> obtenerTotalVentas() {
        VentaTotalResponse totalVentas = ventaService.obtenerTotalVentas();
        return ResponseEntity.ok(totalVentas);
    }
}
