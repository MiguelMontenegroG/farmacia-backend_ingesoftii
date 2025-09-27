package com.farmacia.model;

public class Direccion {
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String referencias;
    private boolean principal;

    // Constructores
    public Direccion() {}

    public Direccion(String nombre, String apellido, String direccion, String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getReferencias() { return referencias; }
    public void setReferencias(String referencias) { this.referencias = referencias; }

    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
}
