package com.productos.productos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * ===============================
 * PRUEBAS DEL MICROSERVICIO DE PRODUCTOS
 * ===============================
 * 
 * Pruebas de integración del microservicio de gestión de productos.
 * Utiliza el perfil 'test' que configura H2 en memoria.
 */
@SpringBootTest
@ActiveProfiles("test")
class ProductoApplicationTests {

	@Test
	void contextLoads() {
		// Verifica que el contexto de Spring Boot se carga correctamente
		// con todas las dependencias del microservicio de productos
	}

}