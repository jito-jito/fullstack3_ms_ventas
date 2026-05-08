package com.ventas.ventas.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GlobalExceptionHandler - Pruebas Unitarias")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        webRequest = new ServletWebRequest(new MockHttpServletRequest("GET", "/api/ventas/1"));
    }

    // =========================================================
    // ResourceNotFoundException -> 404
    // =========================================================

    @Test
    @DisplayName("handleResourceNotFoundException - retorna 404 con mensaje correcto")
    void handleResourceNotFoundException_retorna404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Venta no encontrada con ID: 5");

        ResponseEntity<ErrorDetails> response = handler.handleResourceNotFoundException(ex, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Venta no encontrada con ID: 5");
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getDetails()).isNotNull();
    }

    // =========================================================
    // IllegalArgumentException -> 400
    // =========================================================

    @Test
    @DisplayName("handleIllegalArgumentException - retorna 400 con mensaje correcto")
    void handleIllegalArgumentException_retorna400() {
        IllegalArgumentException ex = new IllegalArgumentException("La venta debe tener al menos un detalle");

        ResponseEntity<ErrorDetails> response = handler.handleIllegalArgumentException(ex, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("La venta debe tener al menos un detalle");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
}
