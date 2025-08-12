package com.api.test.demo.excepciones;

public class ExcepcionNotFound extends RuntimeException {
    private final String query;
    public ExcepcionNotFound(String query) {
        super("No se encuentran noticias para el texto: " + query);
        this.query = query;
    }
    public String getQuery() { return query; }
}
