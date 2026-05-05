package com.ventas.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ===============================
 * MICROSERVICIO DE VENTAS
 * ===============================
 * 
 * Clase principal del microservicio de gestión de ventas.
 * Proporciona funcionalidades de:
 * - Registro de ventas de productos
 * - Consulta de ventas realizadas
 * - Ventas totales por producto
 * - Ventas totales del ecommerce
 * 
 * Puerto: 8083
 * Base URL: /api/ventas
 */
@SpringBootApplication
public class VentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentaApplication.class, args);
		System.out.println("🚀 Microservicio de Ventas iniciado en puerto 8083");
		System.out.println("🛒 API disponible en: http://localhost:8083/api/ventas");
	}

}
