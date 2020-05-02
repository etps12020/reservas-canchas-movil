package com.example.databases.model;

import androidx.annotation.NonNull;

public class TipoCancha
{
    private int idTipoCancha;
    private String tipo;


    public int getIdTipoCancha() {
        return idTipoCancha;
    }

    public void setIdTipoCancha(int idTipoCancha) {
        this.idTipoCancha = idTipoCancha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public String toString() {
        return tipo.toString() ;
    }




}
