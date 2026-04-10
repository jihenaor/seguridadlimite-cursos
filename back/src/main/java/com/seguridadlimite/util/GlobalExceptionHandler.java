package com.seguridadlimite.util;

import com.seguridadlimite.models.permiso.application.dto.PermisoSolapamientoResponse;
import com.seguridadlimite.models.permiso.application.exception.PermisoSolapamientoException;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PermisoSolapamientoException.class)
    public ResponseEntity<PermisoSolapamientoResponse> handlePermisoSolapamiento(PermisoSolapamientoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(PermisoSolapamientoResponse.of(e.getMessage(), e.getConflictos()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse;

        if (e instanceof BusinessException) {
            errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } else if (e instanceof AuthenticationException authEx) {
            errorResponse = new ErrorResponse(authEx.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } else if (e instanceof DataIntegrityViolationException dataintegrityviolationexception) {
            String message = extractConstraintViolationMessage(dataintegrityviolationexception);
            errorResponse = new ErrorResponse(message);
            log.error("Error de integridad de datos: {}", message, dataintegrityviolationexception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else {
            errorResponse = new ErrorResponse("Se ha producido un error en el servidor.");
            log.error("Error inesperado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private String extractConstraintViolationMessage(DataIntegrityViolationException e) {
        if (e.getCause() instanceof ConstraintViolationException cve) {
            return cve.getSQLException().getMessage();
        }
        return "Violación de la integridad de los datos.";
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
    }
}
