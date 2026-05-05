package com.ventas.ventas.service;

// ===============================
// IMPORTACIONES DE SPRING
// ===============================
import org.springframework.stereotype.Service;

import com.ventas.ventas.dto.VentaTotalProductoResponse;
import com.ventas.ventas.dto.VentaTotalResponse;
import com.ventas.ventas.exception.ResourceNotFoundException;
import com.ventas.ventas.model.DetalleVenta;
import com.ventas.ventas.model.Venta;
import com.ventas.ventas.repository.DetalleVentaRepository;
import com.ventas.ventas.repository.VentaRepository;

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
 * SERVICIO: VentaService
 * ===============================
 *
 * Contiene la lógica de negocio del microservicio de ventas.
 * Incluye operaciones CRUD básicas y consultas de totales.
 */
@Service
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public VentaService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    // ==========================================================
    // OBTENER TODAS LAS VENTAS
    // ==========================================================

    public List<Venta> obtenerTodas() {
        log.info("Solicitando listado completo de ventas.");
        List<Venta> ventas = ventaRepository.findAll();
        log.info("Se encontraron {} ventas registradas.", ventas.size());
        return ventas;
    }

    // ==========================================================
    // OBTENER VENTA POR ID
    // ==========================================================

    public Venta obtenerPorId(Long id) {
        log.info("Buscando venta con ID: {}", id);
        return ventaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró una venta con ID: {}", id);
                    return new ResourceNotFoundException("Venta no encontrada con ID: " + id);
                });
    }

    // ==========================================================
    // REGISTRAR NUEVA VENTA
    // ==========================================================

    public Venta registrar(Venta venta) {
        log.info("Registrando nueva venta con {} detalles.", venta.getDetalles().size());

        // Validar que la venta tenga al menos un detalle
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            log.warn("Intento de registrar venta sin detalles.");
            throw new IllegalArgumentException("La venta debe tener al menos un detalle");
        }

        // Calcular subtotales y total
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta detalle : venta.getDetalles()) {
            // Validar campos del detalle
            if (detalle.getCantidad() == null || detalle.getCantidad() < 1) {
                log.warn("Cantidad inválida en detalle de venta: {}", detalle.getCantidad());
                throw new IllegalArgumentException("La cantidad de cada producto debe ser al menos 1");
            }

            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("Precio unitario inválido en detalle de venta: {}", detalle.getPrecioUnitario());
                throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
            }

            // Calcular subtotal del detalle
            BigDecimal subtotal = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotal);

            // Establecer referencia bidireccional
            detalle.setVenta(venta);

            total = total.add(subtotal);
        }

        // Establecer total y estado
        venta.setTotal(total);
        venta.setActivo(true);

        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta registrada correctamente con ID: {} | Total: ${}", 
                ventaGuardada.getId(), ventaGuardada.getTotal());
        return ventaGuardada;
    }

    // ==========================================================
    // ELIMINAR VENTA (SOFT DELETE - cambiar a inactivo)
    // ==========================================================

    public void eliminar(Long id) {
        log.info("Intentando desactivar venta con ID: {}", id);

        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede eliminar. Venta no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Venta no encontrada con ID: " + id);
                });

        // Soft delete - cambiar estado a inactivo
        ventaExistente.setActivo(false);
        ventaRepository.save(ventaExistente);

        log.info("Venta desactivada correctamente con ID: {}", id);
    }

    // ==========================================================
    // OBTENER VENTAS TOTALES POR PRODUCTO
    // ==========================================================

    public VentaTotalProductoResponse obtenerTotalPorProducto(Long productoId) {
        log.info("Consultando ventas totales del producto con ID: {}", productoId);

        // Verificar que existan ventas para ese producto
        if (!detalleVentaRepository.existsByProductoId(productoId)) {
            log.warn("No se encontraron ventas para el producto con ID: {}", productoId);
            throw new ResourceNotFoundException("No se encontraron ventas para el producto con ID: " + productoId);
        }

        // Obtener totales
        Long cantidadTotal = detalleVentaRepository.sumCantidadByProductoId(productoId);
        BigDecimal montoTotal = detalleVentaRepository.sumSubtotalByProductoId(productoId);

        // Obtener nombre del producto desde los detalles
        List<String> nombres = detalleVentaRepository.findProductoNombreByProductoId(productoId);
        String productoNombre = nombres.isEmpty() ? "Producto desconocido" : nombres.get(0);

        VentaTotalProductoResponse response = new VentaTotalProductoResponse(
                productoId,
                productoNombre,
                cantidadTotal,
                montoTotal
        );

        log.info("Totales del producto '{}': {} unidades vendidas, monto total: ${}",
                productoNombre, cantidadTotal, montoTotal);

        return response;
    }

    // ==========================================================
    // OBTENER VENTAS TOTALES DEL ECOMMERCE
    // ==========================================================

    public VentaTotalResponse obtenerTotalVentas() {
        log.info("Consultando ventas totales del ecommerce.");

        BigDecimal montoTotal = ventaRepository.sumTotalVentasActivas();
        Long cantidadVentas = ventaRepository.countVentasActivas();
        Long cantidadProductosVendidos = detalleVentaRepository.sumCantidadTotal();

        VentaTotalResponse response = new VentaTotalResponse(
                montoTotal,
                cantidadVentas,
                cantidadProductosVendidos
        );

        log.info("Totales del ecommerce: {} ventas, {} productos vendidos, monto total: ${}",
                cantidadVentas, cantidadProductosVendidos, montoTotal);

        return response;
    }
}
