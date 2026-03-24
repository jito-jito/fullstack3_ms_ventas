package com.productos.productos.exception;

import java.time.LocalDateTime;

/**
 * ===============================
 * CLASE: ErrorDetails
 * ===============================
 *
 * Modelo para representar los detalles de un error.
 * Se utiliza para estructurar las respuestas de error del API.
 */
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    /**
     * Constructor con todos los parámetros
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Constructor vacío
     */
    public ErrorDetails() {
    }

    // ===============================
    // GETTERS Y SETTERS
    // ===============================

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}