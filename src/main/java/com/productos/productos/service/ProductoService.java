package com.productos.productos.service;

// ===============================
// IMPORTACIONES DE SPRING
// ===============================
import org.springframework.stereotype.Service;

import com.productos.productos.exception.ResourceNotFoundException;
import com.productos.productos.model.Producto;
import com.productos.productos.repository.ProductoRepository;

// ===============================
// IMPORTACIONES DE LOMBOK
// ===============================
import lombok.extern.slf4j.Slf4j;

// ===============================
// IMPORTACIONES DE JAVA
// ===============================
import java.math.BigDecimal;
import java.util.List;

/**
 * ===============================
 * SERVICIO: ProductoService
 * ===============================
 *
 * Contiene la lógica de negocio del microservicio de productos.
 * Incluye operaciones CRUD básicas.
 */
@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // ==========================================================
    // OBTENER TODOS LOS PRODUCTOS
    // ==========================================================

    public List<Producto> obtenerTodos() {
        log.info("Solicitando listado completo de productos.");
        List<Producto> productos = productoRepository.findAll();
        log.info("Se encontraron {} productos registrados.", productos.size());
        return productos;
    }

    // ==========================================================
    // OBTENER PRODUCTO POR ID
    // ==========================================================

    public Producto obtenerPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró un producto con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
                });
    }

    // ==========================================================
    // GUARDAR NUEVO PRODUCTO
    // ==========================================================

    public Producto guardar(Producto producto) {
        log.info("Guardando nuevo producto con nombre: {}", producto.getNombre());

        // Verificar que el nombre no exista
        if (productoRepository.existsByNombre(producto.getNombre())) {
            log.warn("Nombre de producto ya existe: {}", producto.getNombre());
            throw new IllegalArgumentException("El nombre ya está registrado: " + producto.getNombre());
        }

        // Validar que el precio sea positivo
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Precio inválido para producto: {}", producto.getPrecio());
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        // Validar que el stock no sea negativo
        if (producto.getStock() < 0) {
            log.warn("Stock inválido para producto: {}", producto.getStock());
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        
        Producto productoGuardado = productoRepository.save(producto);
        log.info("Producto guardado correctamente con ID: {}", productoGuardado.getId());
        return productoGuardado;
    }

    // ==========================================================
    // ACTUALIZAR PRODUCTO
    // ==========================================================

    public Producto actualizar(Long id, Producto productoActualizado) {
        log.info("Intentando actualizar producto con ID: {}", id);

        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede actualizar. Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
                });

        // Verificar que el nombre no esté en uso por otro producto
        if (!productoExistente.getNombre().equals(productoActualizado.getNombre()) 
            && productoRepository.existsByNombre(productoActualizado.getNombre())) {
            log.warn("Nombre de producto ya existe: {}", productoActualizado.getNombre());
            throw new IllegalArgumentException("El nombre ya está registrado: " + productoActualizado.getNombre());
        }

        // Validar precio
        if (productoActualizado.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Precio inválido para producto: {}", productoActualizado.getPrecio());
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        // Validar stock
        if (productoActualizado.getStock() < 0) {
            log.warn("Stock inválido para producto: {}", productoActualizado.getStock());
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        // Actualizar campos
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setStock(productoActualizado.getStock());

        Producto productoGuardado = productoRepository.save(productoExistente);
        log.info("Producto actualizado correctamente con ID: {}", productoGuardado.getId());
        return productoGuardado;
    }

    // ==========================================================
    // ELIMINAR PRODUCTO (SOFT DELETE)
    // ==========================================================

    public void eliminar(Long id) {
        log.info("Intentando desactivar producto con ID: {}", id);

        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede eliminar. Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
                });

        // Soft delete - cambiar estado a inactivo
        productoExistente.setActivo(false);
        productoRepository.save(productoExistente);

        log.info("Producto desactivado correctamente con ID: {}", id);
    }
}