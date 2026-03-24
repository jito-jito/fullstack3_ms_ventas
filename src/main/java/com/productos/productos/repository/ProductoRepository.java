package com.productos.productos.repository;

// ===============================
// IMPORTACIÓN DE SPRING DATA JPA
// ===============================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productos.productos.model.Producto;

/**
 * ===============================
 * REPOSITORY: ProductoRepository
 * ===============================
 *
 * Interfaz de acceso a datos para la entidad Producto.
 * Proporciona operaciones CRUD básicas.
 *
 * Spring Data JPA generará automáticamente la implementación.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Verifica si existe un producto con el nombre dado
     * @param nombre nombre a verificar
     * @return true si existe, false si no
     */
    boolean existsByNombre(String nombre);
}