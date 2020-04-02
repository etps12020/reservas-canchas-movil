package com.example.reservas.model;

import androidx.annotation.NonNull;

public class RolUsuario {

    private int idRolUsuario;
    private String rol;

    public int getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(int idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    @NonNull
    @Override
    public String toString() {
        return this.rol;
    }
}
