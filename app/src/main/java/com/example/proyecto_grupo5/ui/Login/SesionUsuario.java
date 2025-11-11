package com.example.proyecto_grupo5.ui.Login;

public class SesionUsuario {
    private static int idUsuario;
    private static String nombre;
    private static String rol;

    public static void setIdUsuario(int id) {
        idUsuario = id;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static void setNombre(String n) {
        nombre = n;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setRol(String r) {
        rol = r;
    }

    public static String getRol() {
        return rol;
    }
}
