package com.api.test.demo.excepciones;

import com.api.test.demo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcepcionHandler {

    @ExceptionHandler(ExcepcionBadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ExcepcionBadRequest ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("g268", "Parámetros inválidos"));
    }

    @ExceptionHandler(ExcepcionNotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ExcepcionNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("g267", "No se encuentran noticias para el texto: " + ex.getQuery()));
    }

    @ExceptionHandler(ExcepcionInterna.class)
    public ResponseEntity<ErrorResponse> handleInternal(ExcepcionInterna ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("g100", "Error interno del servidor"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("g100", "Error interno del servidor"));
    }
}
