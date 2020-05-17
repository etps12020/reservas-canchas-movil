package com.example.databases.api.usuarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateUserAsistente {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUpdateUserAsistente() {
    }

    /**
     *
     * @param password
     * @param id
     * @param telefono
     */
    public RequestUpdateUserAsistente(Integer id, String telefono, String password) {
        super();
        this.id = id;
        this.telefono = telefono;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}