package com.utp.donlimon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Respuestas de error uniformes para la API REST: { "campo": ["mensaje", ...] }. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Falla una validación (@Valid): responde 400 con los mensajes agrupados por campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> validacion(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errores
                .computeIfAbsent(error.getField(), campo -> new ArrayList<>())
                .add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

    // El JSON llega mal escrito o con un tipo de dato incorrecto: responde 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> jsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "El cuerpo de la petición no es un JSON válido o tiene un tipo de dato incorrecto"));
    }

    // La base de datos rechaza el registro (por ejemplo, nombre duplicado): responde 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> integridad(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "La operación viola una restricción de la base de datos (por ejemplo, un nombre duplicado)"));
    }
}