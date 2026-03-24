package com.productos.productos.exception;

/**
 * ===============================
 * EXCEPCIÓN: ResourceNotFoundException
 * ===============================
 *
 * Excepción personalizada que se lanza cuando no se encuentra
 * un producto solicitado en la base de datos.
 *
 * Esta excepción se puede lanzar desde el servicio cuando:
 * - Se busca un producto por ID que no existe
 * - Se busca un producto por nombre que no existe
 * - Se intenta actualizar un producto que no existe
 * - Se intenta eliminar un producto que no existe
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