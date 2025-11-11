package com.example.proyecto_grupo5.ui.Admin;

public class Admin_ModeloAgente {
    private int id;
    private String nombre;
    private String correo;
    private String codigo;
    private String area;
    private String estado;

    public Admin_ModeloAgente(int id, String nombre, String correo, String codigo, String area, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.codigo = codigo;
        this.area = area;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getCodigo() { return codigo; }
    public String getArea() { return area; }
    public String getEstado() { return estado; }
}
