package com.ventas.ventas.exception;

/**
 * ===============================
 * EXCEPCIÓN: ResourceNotFoundException
 * ===============================
 *
 * Excepción personalizada que se lanza cuando no se encuentra
 * una venta solicitada en la base de datos.
 *
 * Esta excepción se puede lanzar desde el servicio cuando:
 * - Se busca una venta por ID que no existe
 * - Se intenta consultar totales de un producto sin ventas
 * - Se intenta eliminar una venta que no existe
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que acepta solo un mensaje de error
     *
     * @param message mensaje descriptivo del error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta mensaje y causa del error
     *
     * @param message mensaje descriptivo del error
     * @param cause causa original de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
