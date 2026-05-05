# Microservicio de Gestión de Ventas

## Descripción
Microservicio para la gestión de ventas de productos del ecommerce Shopiline.
Permite registrar ventas, consultar el historial y obtener reportes de ventas totales por producto y del ecommerce completo.

## Características
- ✅ Registrar ventas con múltiples productos
- ✅ Consultar historial de ventas
- ✅ Obtener ventas totales por producto
- ✅ Obtener ventas totales del ecommerce
- ✅ Eliminación lógica de ventas (soft delete)
- ✅ Validaciones de datos
- ✅ Manejo global de excepciones

## Configuración
- **Puerto**: 8083
- **Base URL**: `http://localhost:8083/api/ventas`
- **Base de datos**: Oracle Cloud

## Endpoints Disponibles

### Ventas CRUD
- `POST /api/ventas` - Registrar nueva venta
- `GET /api/ventas` - Obtener todas las ventas
- `GET /api/ventas/{id}` - Obtener venta por ID
- `DELETE /api/ventas/{id}` - Eliminar venta (soft delete)

### Reportes de Totales
- `GET /api/ventas/total` - Obtener ventas totales del ecommerce
- `GET /api/ventas/producto/{productoId}/total` - Obtener ventas totales de un producto


## Modelo de Venta
```json
{
  "id": 1,
  "detalles": [
    {
      "id": 1,
      "productoId": 5,
      "productoNombre": "Laptop Gamer",
      "cantidad": 2,
      "precioUnitario": 129990,
      "subtotal": 259980
    },
    {
      "id": 2,
      "productoId": 3,
      "productoNombre": "Mouse Inalámbrico",
      "cantidad": 1,
      "precioUnitario": 15990,
      "subtotal": 15990
    }
  ],
  "total": 275970,
  "activo": true,
  "fechaVenta": "2026-05-04T21:30:00",
  "fechaCreacion": "2026-05-04T21:30:00",
  "fechaActualizacion": "2026-05-04T21:30:00"
}
```

## Respuesta de Ventas Totales por Producto
```json
{
  "productoId": 5,
  "productoNombre": "Laptop Gamer",
  "cantidadTotalVendida": 25,
  "montoTotal": 3249750.00
}
```

## Respuesta de Ventas Totales del Ecommerce
```json
{
  "montoTotal": 15750000.00,
  "cantidadVentas": 150,
  "cantidadProductosVendidos": 520
}
```

## Validaciones
- Detalles: la venta debe tener al menos un detalle
- Producto ID: requerido en cada detalle
- Producto Nombre: requerido, máximo 100 caracteres
- Cantidad: requerida, mínimo 1
- Precio Unitario: requerido, mayor a 0, máximo 10 dígitos enteros y 2 decimales
- Subtotal y Total: calculados automáticamente por el servicio

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

### Registrar una venta
```bash
curl -X POST http://localhost:8083/api/ventas \
  -H "Content-Type: application/json" \
  -d '{
    "detalles": [
      {
        "productoId": 1,
        "productoNombre": "iPhone 15",
        "cantidad": 2,
        "precioUnitario": 999.99
      },
      {
        "productoId": 3,
        "productoNombre": "Carcasa Protectora",
        "cantidad": 2,
        "precioUnitario": 29.99
      }
    ]
  }'
```

### Obtener todas las ventas
```bash
curl "http://localhost:8083/api/ventas"
```

### Obtener venta por ID
```bash
curl "http://localhost:8083/api/ventas/1"
```

### Obtener ventas totales del ecommerce
```bash
curl "http://localhost:8083/api/ventas/total"
```

### Obtener ventas totales de un producto
```bash
curl "http://localhost:8083/api/ventas/producto/1/total"
```

### Eliminar venta (soft delete)
```bash
curl -X DELETE "http://localhost:8083/api/ventas/1"
```