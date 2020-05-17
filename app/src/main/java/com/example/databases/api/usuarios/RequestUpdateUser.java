package com.example.databases.api.usuarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestUpdateUser {

    @SerializedName("usuloguedo")
    @Expose
    private Integer usuloguedo;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dui")
    @Expose
    private String dui;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("carnet")
    @Expose
    private String carnet;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rol")
    @Expose
    private Integer rol;
    @SerializedName("estado")
    @Expose
    private Integer estado;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUpdateUser() {
    }

    /**
     *
     * @param password
     * @param estado
     * @param carnet
     * @param correo
     * @param dui
     * @param id
     * @param telefono
     * @param usuloguedo
     * @param nombre
     * @param rol
     */
    public RequestUpdateUser(Integer usuloguedo, Integer id, String dui, String nombre, String correo, String carnet, String telefono, String password, Integer rol, Integer estado) {
        super();
        this.usuloguedo = usuloguedo;
        this.id = id;
        this.dui = dui;
        this.nombre = nombre;
        this.correo = correo;
        this.carnet = carnet;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public Integer getUsuloguedo() {
        return usuloguedo;
    }

    public void setUsuloguedo(Integer usuloguedo) {
        this.usuloguedo = usuloguedo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
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

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }


}
