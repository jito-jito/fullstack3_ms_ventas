# Microservicio de Gestión de Productos

## Descripción
Microservicio para la gestión básica de productos con operaciones CRUD.

## Características
- ✅ Crear, obtener, actualizar y eliminar productos (soft delete)
- ✅ Validaciones de datos
- ✅ Manejo global de excepciones

## Configuración
- **Puerto**: 8082
- **Base URL**: `http://localhost:8082/api/productos`
- **Base de datos**: Oracle Cloud

## Endpoints Disponibles

### Productos CRUD
- `POST /api/productos` - Crear producto
- `GET /api/productos` - Obtener todos los productos
- `GET /api/productos/{id}` - Obtener producto por ID
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto (soft delete)


## Modelo de Producto
```json
{
  "id": 1,
  "nombre": "Laptop Gamer",
  "descripcion": "Laptop para gaming de alta performance",
  "precio": 129990,
  "categoria": "Electrónicos",
  "stock": 15,
  "activo": true,
  "fechaCreacion": "2026-03-23T10:30:00",
  "fechaActualizacion": "2026-03-23T10:30:00"
}
```

## Validaciones
- Nombre: requerido, 2-100 caracteres
- Precio: requerido, mayor a 0, máximo 10 dígitos enteros y 2 decimales
- Categoría: requerida, máximo 50 caracteres
- Stock: requerido, no negativo
- Descripción: opcional, máximo 500 caracteres

## Cómo ejecutar
```bash
# Compilar
./mvnw clean compile

# Ejecutar
./mvnw spring-boot:run

# Ejecutar tests
./mvnw test
```

## Ejemplos de uso

### Crear producto
```bash
curl -X POST http://localhost:8082/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "iPhone 15",
    "descripcion": "Smartphone Apple última generación", 
    "precio": 999.99,
    "categoria": "Electrónicos",
    "stock": 50
  }'
```

### Obtener todos los productos
```bash
curl "http://localhost:8082/api/productos"
```

### Obtener producto por ID
```bash
curl "http://localhost:8082/api/productos/1"
```

### Actualizar producto
```bash
curl -X PUT http://localhost:8082/api/productos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "iPhone 15 Pro",
    "descripcion": "Smartphone Apple última generación Pro", 
    "precio": 1199.99,
    "categoria": "Electrónicos",
    "stock": 30
  }'
```

### Eliminar producto (soft delete)
```bash
curl -X DELETE "http://localhost:8082/api/productos/1"
```