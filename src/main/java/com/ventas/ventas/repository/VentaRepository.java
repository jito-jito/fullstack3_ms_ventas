package com.ventas.ventas.repository;

// ===============================
// IMPORTACIÓN DE SPRING DATA JPA
// ===============================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ventas.ventas.model.Venta;

import java.math.BigDecimal;
import java.util.List;

/**
 * ===============================
 * REPOSITORY: VentaRepository
 * ===============================
 *
 * Interfaz de acceso a datos para la entidad Venta.
 * Proporciona operaciones CRUD básicas y consultas personalizadas
 * para obtener totales de ventas del ecommerce.
 *
 * Spring Data JPA generará automáticamente la implementación.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    /**
     * Obtiene todas las ventas activas
     * @return lista de ventas activas
     */
    List<Venta> findByActivoTrue();

    /**
     * Calcula el monto total de todas las ventas activas
     * @return monto total de ventas
     */
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.activo = true")
    BigDecimal sumTotalVentasActivas();

    /**
     * Cuenta el número total de ventas activas
     * @return cantidad de ventas activas
     */
    @Query("SELECT COUNT(v) FROM Venta v WHERE v.activo = true")
    Long countVentasActivas();
}
