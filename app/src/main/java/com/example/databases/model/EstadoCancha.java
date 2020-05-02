package com.example.databases.model;

import androidx.annotation.NonNull;

public class EstadoCancha
{
    private int idEstado;
    private String estado;


    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return estado.toString() ;
    }


}
