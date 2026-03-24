package com.productos.productos.controller;

// ===============================
// IMPORTACIONES SPRING
// ===============================
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.productos.productos.model.Producto;
import com.productos.productos.service.ProductoService;

// ===============================
// VALIDACIONES
// ===============================
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ===============================
 * CONTROLLER: ProductoController
 * ===============================
 *
 * Maneja las solicitudes HTTP del cliente para gestión de productos.
 * Endpoints habilitados: crear, modificar, eliminar, buscar y listar.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ==========================================================
    // CREAR PRODUCTO
    // ==========================================================

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // ==========================================================
    // OBTENER TODOS LOS PRODUCTOS
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    // ==========================================================
    // OBTENER PRODUCTO POR ID
    // ==========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // ==========================================================
    // ACTUALIZAR PRODUCTO
    // ==========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // ==========================================================
    // ELIMINAR PRODUCTO (SOFT DELETE - cambiar a inactivo)
    // ==========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}