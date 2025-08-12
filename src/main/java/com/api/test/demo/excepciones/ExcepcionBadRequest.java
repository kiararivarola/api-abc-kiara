package com.api.test.demo.excepciones;

public class ExcepcionBadRequest extends RuntimeException {
    public ExcepcionBadRequest() { super("Parámetros inválidos"); }
}
