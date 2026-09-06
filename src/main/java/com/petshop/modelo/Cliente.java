package com.petshop.modelo;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private int creadoPor;

    public Cliente() {}

    public Cliente(int id, String nombre, String telefono, String email, int creadoPor) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.creadoPor = creadoPor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getCreadoPor() { return creadoPor; }
    public void setCreadoPor(int creadoPor) { this.creadoPor = creadoPor; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - Tel: " + telefono + " - " + email;
    }
}
