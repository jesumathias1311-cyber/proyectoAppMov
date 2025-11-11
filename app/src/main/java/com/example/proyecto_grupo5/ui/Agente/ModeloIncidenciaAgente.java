package com.example.proyecto_grupo5.ui.Agente;

public class ModeloIncidenciaAgente {
    private int id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String estado;
    private String tipo;

    public ModeloIncidenciaAgente(int id, String titulo, String descripcion, String ubicacion, String estado, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getUbicacion() { return ubicacion; }
    public String getEstado() { return estado; }
    public String getTipo() { return tipo; }
}
