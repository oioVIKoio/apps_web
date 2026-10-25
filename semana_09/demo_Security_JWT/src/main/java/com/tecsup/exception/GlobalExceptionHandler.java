package com.tecsup.exception;

import com.tecsup.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Extra: la guía no maneja errores de validación ni credenciales incorrectas
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid -> 400 con el mensaje de cada campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }

    // Email o contraseña incorrectos en /signin -> 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponse> manejarCredenciales(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Error: Credenciales incorrectas"));
    }
}
