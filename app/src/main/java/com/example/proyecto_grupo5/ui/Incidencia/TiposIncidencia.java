package com.example.proyecto_grupo5.ui.Incidencia;

public class TiposIncidencia {
    private int id;
    private String nombre;

    public TiposIncidencia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}
