package com.example.databases.api.utilidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ErrorObject {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    /**
     * No args constructor for use in serialization
     */
    public ErrorObject() {
    }

    /**
     * @param mensaje
     */
    public ErrorObject(String mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}