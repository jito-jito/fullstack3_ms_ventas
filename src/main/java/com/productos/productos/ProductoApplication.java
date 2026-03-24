package com.productos.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ===============================
 * MICROSERVICIO DE PRODUCTOS
 * ===============================
 * 
 * Clase principal del microservicio de gestión de productos.
 * Proporciona funcionalidades de:
 * - Gestión CRUD de productos
 * - Búsquedas por categoría, precio y nombre
 * - Control de stock e inventario
 * - Estados activos/inactivos
 * 
 * Puerto: 8082
 * Base URL: /api/productos
 */
@SpringBootApplication
public class ProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoApplication.class, args);
		System.out.println("🚀 Microservicio de Productos iniciado en puerto 8082");
		System.out.println("📦 API disponible en: http://localhost:8082/api/productos");
	}

}