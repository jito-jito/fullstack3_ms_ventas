package com.ventas.ventas.repository;

// ===============================
// IMPORTACIÓN DE SPRING DATA JPA
// ===============================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ventas.ventas.model.DetalleVenta;

import java.math.BigDecimal;
import java.util.List;

/**
 * ===============================
 * REPOSITORY: DetalleVentaRepository
 * ===============================
 *
 * Interfaz de acceso a datos para la entidad DetalleVenta.
 * Proporciona consultas personalizadas para obtener
 * totales de ventas por producto.
 *
 * Spring Data JPA generará automáticamente la implementación.
 */
@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    /**
     * Calcula la cantidad total vendida de un producto específico
     * Solo considera ventas activas
     * @param productoId ID del producto
     * @return cantidad total vendida
     */
    @Query("SELECT COALESCE(SUM(d.cantidad), 0) FROM DetalleVenta d WHERE d.productoId = :productoId AND d.venta.activo = true")
    Long sumCantidadByProductoId(@Param("productoId") Long productoId);

    /**
     * Calcula el monto total vendido de un producto específico
     * Solo considera ventas activas
     * @param productoId ID del producto
     * @return monto total vendido
     */
    @Query("SELECT COALESCE(SUM(d.subtotal), 0) FROM DetalleVenta d WHERE d.productoId = :productoId AND d.venta.activo = true")
    BigDecimal sumSubtotalByProductoId(@Param("productoId") Long productoId);

    /**
     * Calcula la cantidad total de productos vendidos en todo el ecommerce
     * Solo considera ventas activas
     * @return cantidad total de productos vendidos
     */
    @Query("SELECT COALESCE(SUM(d.cantidad), 0) FROM DetalleVenta d WHERE d.venta.activo = true")
    Long sumCantidadTotal();

    /**
     * Obtiene el nombre del producto más reciente registrado para un productoId
     * @param productoId ID del producto
     * @return lista de nombres (se usa el primero)
     */
    @Query("SELECT d.productoNombre FROM DetalleVenta d WHERE d.productoId = :productoId ORDER BY d.id DESC")
    List<String> findProductoNombreByProductoId(@Param("productoId") Long productoId);

    /**
     * Verifica si existen detalles de venta para un producto específico
     * @param productoId ID del producto
     * @return true si existen ventas del producto
     */
    boolean existsByProductoId(Long productoId);
}
