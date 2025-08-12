package com.api.test.demo.excepciones;

public class ExcepcionInterna extends RuntimeException {
    public ExcepcionInterna(Throwable cause) {
        super("Error interno del servidor", cause);
    }
}
