package com.api.test.demo;

public class ErrorResponse {
    private String codigo;
    private String error;

    public ErrorResponse(String codigo, String error) {
        this.codigo = codigo;
        this.error = error;
    }
    public String getCodigo() { return codigo; }
    public String getError()  { return error; }
}
