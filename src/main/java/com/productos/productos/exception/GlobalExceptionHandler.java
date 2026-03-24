package com.productos.productos.exception;

// ===============================
// IMPORTACIONES
// ===============================

// ResponseEntity permite construir respuestas HTTP completas,
// incluyendo cuerpo, estado y encabezados.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// FieldError permite obtener los mensajes de error de cada campo validado.
import org.springframework.validation.FieldError;

// MethodArgumentNotValidException se lanza cuando falla una validación
// activada con @Valid en el controlador.
import org.springframework.web.bind.MethodArgumentNotValidException;

// @ControllerAdvice permite manejar excepciones de forma global
// para todos los controladores de la aplicación.
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// WebRequest permite obtener información de la solicitud HTTP,
// como la URI donde ocurrió el error.
import org.springframework.web.context.request.WebRequest;

// LocalDateTime se utiliza para registrar la fecha y hora exacta del error.
import java.time.LocalDateTime;

// List y Collectors se usan para construir una lista de mensajes
// provenientes de los errores de validación.
import java.util.List;
import java.util.stream.Collectors;

/**
 * ===============================
 * CLASE: GlobalExceptionHandler
 * ===============================
 *
 * Permite manejar todas las excepciones de la aplicación
 * de forma centralizada para el microservicio de productos.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción personalizada ResourceNotFoundException.
     * Esta se lanza cuando no se encuentra un producto solicitado.
     *
     * @param ex excepción lanzada
     * @param request información de la solicitud HTTP
     * @return respuesta con el error 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, 
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja las excepciones cuando los argumentos de un método no son válidos.
     * Se lanza cuando las validaciones @Valid fallan en el controlador.
     *
     * @param ex excepción lanzada
     * @param request información de la solicitud HTTP
     * @return respuesta con el error 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, 
            WebRequest request) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String mensaje = "Errores de validación: " + String.join(", ", errores);

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                mensaje,
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones de argumentos ilegales (IllegalArgumentException).
     * Se lanza cuando se pasan parámetros no válidos a los métodos.
     *
     * @param ex excepción lanzada
     * @param request información de la solicitud HTTP
     * @return respuesta con el error 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(
            IllegalArgumentException ex, 
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no contemplada específicamente.
     * Actúa como un manejador global para errores inesperados.
     *
     * @param ex excepción lanzada
     * @param request información de la solicitud HTTP
     * @return respuesta con el error 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception ex, 
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Error interno del servidor: " + ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}