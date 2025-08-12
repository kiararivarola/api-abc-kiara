package com.api.test.demo;

public class NoticiaDTO {
    private String fecha;
    private String enlace;
    private String enlace_foto;
    private String titulo;
    private String resumen;

    public NoticiaDTO(String fecha, String enlace, String enlace_foto, String titulo, String resumen) {
        this.fecha = fecha;
        this.enlace = enlace;
        this.enlace_foto = enlace_foto;
        this.titulo = titulo;
        this.resumen = resumen;
    }

    public String getFecha() { return fecha; }
    public String getEnlace() { return enlace; }
    public String getEnlace_foto() { return enlace_foto; }
    public String getTitulo() { return titulo; }
    public String getResumen() { return resumen; }
}
