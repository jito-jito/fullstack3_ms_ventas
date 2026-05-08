# Informe de Pruebas Unitarias — Microservicio `ms-ventas`

**Fecha:** 7 de mayo de 2026  
**Proyecto:** Microservicio de Gestión de Ventas — Shopiline  
**Framework de pruebas:** JUnit 5 + Mockito + Spring MockMvc  
**Cobertura:** JaCoCo 0.8.11  

---

## Resultado General

| Métrica | Valor |
|---|---|
| Total de tests | **47** |
| Exitosos | **47** |
| Fallidos | **0** |
| Errores | **0** |
| Omitidos | **0** |
| Cobertura de líneas | **≥ 80%** ✅ |
| Estado del build | **BUILD SUCCESS** ✅ |

---

## Resultados por Suite de Pruebas

| Suite | Tests | Fallos | Errores | Tiempo |
|---|---|---|---|---|
| `VentaServiceTest` | 20 | 0 | 0 | 0.136 s |
| `VentaControllerTest` | 12 | 0 | 0 | 1.868 s |
| `DtoTest` | 6 | 0 | 0 | 0.096 s |
| `VentaTest` | 4 | 0 | 0 | 0.014 s |
| `GlobalExceptionHandlerTest` | 2 | 0 | 0 | 0.002 s |
| `DetalleVentaTest` | 2 | 0 | 0 | 0.002 s |
| `VentaApplicationTests` | 1 | 0 | 0 | 2.625 s |
| **TOTAL** | **47** | **0** | **0** | **~4.7 s** |

---

## Detalle por Suite

### 1. VentaServiceTest — 20 tests

Cubre la capa de servicio (`VentaService`) con mocks de repositorios mediante Mockito.

| # | Test | Escenario cubierto |
|---|---|---|
| 1 | `obtenerTodas_retornaListaVentas` | Retorna lista completa de ventas registradas |
| 2 | `obtenerTodas_listaVacia` | Retorna lista vacía cuando no hay ventas |
| 3 | `obtenerPorId_ventaEncontrada` | Encuentra una venta por su ID válido |
| 4 | `obtenerPorId_noEncontrada_lanzaExcepcion` | Lanza `ResourceNotFoundException` con ID inexistente |
| 5 | `registrar_calculaTotalesCorrectamente` | Calcula subtotal y total automáticamente al registrar |
| 6 | `registrar_multiplesDetalles_sumaTotal` | Suma correcta con múltiples productos en la misma venta |
| 7 | `registrar_sinDetalles_lanzaExcepcion` | Lanza excepción cuando los detalles son `null` |
| 8 | `registrar_detallesVacios_lanzaExcepcion` | Lanza `IllegalArgumentException` con lista de detalles vacía |
| 9 | `registrar_cantidadNula_lanzaExcepcion` | Rechaza cantidad `null` en un detalle |
| 10 | `registrar_cantidadCero_lanzaExcepcion` | Rechaza cantidad igual a 0 |
| 11 | `registrar_precioNulo_lanzaExcepcion` | Rechaza precio unitario `null` |
| 12 | `registrar_precioCero_lanzaExcepcion` | Rechaza precio unitario igual a 0 |
| 13 | `registrar_precioNegativo_lanzaExcepcion` | Rechaza precio unitario negativo |
| 14 | `eliminar_softDelete_exitoso` | Cambia `activo = false` sin eliminar el registro físicamente |
| 15 | `eliminar_noExiste_lanzaExcepcion` | Lanza `ResourceNotFoundException` al eliminar ID inexistente |
| 16 | `obtenerTotalPorProducto_retornaTotales` | Retorna cantidad vendida y monto total por producto |
| 17 | `obtenerTotalPorProducto_sinNombre_usaNombrePorDefecto` | Usa "Producto desconocido" cuando no hay nombre disponible |
| 18 | `obtenerTotalPorProducto_sinVentas_lanzaExcepcion` | Lanza excepción si el producto no tiene ventas registradas |
| 19 | `obtenerTotalVentas_retornaTotales` | Retorna totales consolidados del ecommerce |
| 20 | `obtenerTotalVentas_sinVentas_retornaCeros` | Retorna ceros cuando no hay ventas activas |

---

### 2. VentaControllerTest — 12 tests

Cubre la capa de controlador (`VentaController`) usando Spring MockMvc en modo standalone, verificando códigos HTTP y cuerpos de respuesta JSON.

| # | Test | Endpoint | HTTP esperado |
|---|---|---|---|
| 1 | `registrarVenta_exitoso_retorna201` | `POST /api/ventas` | 201 Created |
| 2 | `registrarVenta_sinDetalles_retorna400` | `POST /api/ventas` | 400 Bad Request (`@Valid`) |
| 3 | `registrarVenta_illegalArgument_retorna400` | `POST /api/ventas` | 400 Bad Request (negocio) |
| 4 | `obtenerTodas_retornaLista200` | `GET /api/ventas` | 200 OK con lista |
| 5 | `obtenerTodas_listaVacia_retorna200` | `GET /api/ventas` | 200 OK con array vacío |
| 6 | `obtenerPorId_encontrada_retorna200` | `GET /api/ventas/1` | 200 OK |
| 7 | `obtenerPorId_noEncontrada_retorna404` | `GET /api/ventas/99` | 404 Not Found |
| 8 | `eliminarVenta_exitoso_retorna204` | `DELETE /api/ventas/1` | 204 No Content |
| 9 | `eliminarVenta_noEncontrada_retorna404` | `DELETE /api/ventas/99` | 404 Not Found |
| 10 | `obtenerTotalVentas_retorna200` | `GET /api/ventas/total` | 200 OK con totales |
| 11 | `obtenerTotalPorProducto_retorna200` | `GET /api/ventas/producto/10/total` | 200 OK |
| 12 | `obtenerTotalPorProducto_sinVentas_retorna404` | `GET /api/ventas/producto/99/total` | 404 Not Found |

---

### 3. DtoTest — 6 tests

Cubre los constructores, getters y setters de las clases de transferencia de datos.

| # | Test | Clase |
|---|---|---|
| 1 | `ventaTotalResponse_constructorConParametros` | `VentaTotalResponse` |
| 2 | `ventaTotalResponse_setters` | `VentaTotalResponse` |
| 3 | `ventaTotalProductoResponse_constructorConParametros` | `VentaTotalProductoResponse` |
| 4 | `ventaTotalProductoResponse_setters` | `VentaTotalProductoResponse` |
| 5 | `errorDetails_constructorConParametros` | `ErrorDetails` |
| 6 | `errorDetails_setters` | `ErrorDetails` |

---

### 4. VentaTest — 4 tests

Cubre el modelo de entidad `Venta`.

| # | Test | Escenario cubierto |
|---|---|---|
| 1 | `gettersSetters_funcionan` | Verifica todos los getters y setters |
| 2 | `toString_incluyeCamposPrincipales` | Verifica que `toString()` incluye ID y total |
| 3 | `activoEsTruePorDefecto` | Valor por defecto de `activo = true` |
| 4 | `detallesIniciadosVaciosPorDefecto` | Lista de detalles no es `null` por defecto |

---

### 5. GlobalExceptionHandlerTest — 2 tests

Cubre el manejo centralizado de excepciones (`GlobalExceptionHandler`).

| # | Test | Escenario cubierto |
|---|---|---|
| 1 | `handleResourceNotFoundException_retorna404` | `ResourceNotFoundException` → 404 Not Found |
| 2 | `handleIllegalArgumentException_retorna400` | `IllegalArgumentException` → 400 Bad Request |

---

### 6. DetalleVentaTest — 2 tests

Cubre el modelo de entidad `DetalleVenta`.

| # | Test | Escenario cubierto |
|---|---|---|
| 1 | `constructorConParametros_calculaSubtotal` | Constructor calcula subtotal = `cantidad × precioUnitario` |
| 2 | `gettersSetters_funcionan` | Verifica todos los getters y setters incluyendo referencia a `Venta` |

---

### 7. VentaApplicationTests — 1 test

| # | Test | Escenario cubierto |
|---|---|---|
| 1 | `contextLoads` | El contexto de Spring Boot se inicializa correctamente con perfil `test` (H2) |

---

## Configuración de Cobertura (JaCoCo)

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```

| Parámetro | Valor |
|---|---|
| Contador | Líneas (`LINE`) |
| Umbral mínimo | 80% (`0.80`) |
| Reporte HTML | `target/site/jacoco/index.html` |
| Archivo de ejecución | `target/jacoco.exec` |
| Resultado | ✅ Superado |

Para generar el reporte de cobertura:

```bash
./mvnw verify -Dspring.profiles.active=test
```

---

## Configuración de Base de Datos para Pruebas

Las pruebas utilizan **H2 en memoria** (perfil `test`) para evitar dependencia de Oracle Cloud durante la ejecución de tests.

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
server.port=0
```

---

## Comandos de Ejecución

```bash
# Ejecutar solo los tests
./mvnw test -Dspring.profiles.active=test

# Ejecutar tests + generar reporte de cobertura JaCoCo
./mvnw verify -Dspring.profiles.active=test

# Ejecutar tests + enviar análisis a SonarQube
./mvnw clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=ms_ventas \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=<TOKEN>
```
