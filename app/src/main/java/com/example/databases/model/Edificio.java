package com.example.databases.model;

import androidx.annotation.NonNull;

public class Edificio {

    private int idEdificio;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String fechaCreacion;
    private EstadoEdificio estadoEdificio;

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoEdificio getEstadoEdificio() {
        return estadoEdificio;
    }

    public void setEstadoEdificio(EstadoEdificio estadoEdificio) {
        this.estadoEdificio = estadoEdificio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getNombre();
    }
}
