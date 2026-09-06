package com.petshop.modelo;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String nombreCompleto;
    private Rol rol;

    public Usuario() {}

    public Usuario(int id, String nombreUsuario, String contrasena, String nombreCompleto, Rol rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombreCompleto + " (" + nombreUsuario + ") - " + rol;
    }
}
