package com.example.databases.api.usuarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseLogin {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("carnet")
    @Expose
    private String carnet;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("idRol")
    @Expose
    private Integer idRol;
    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("idEstado")
    @Expose
    private Integer idEstado;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseLogin() {
    }

    /**
     *
     * @param password
     * @param idRol
     * @param idEstado
     * @param estado
     * @param carnet
     * @param correo
     * @param fechaCreacion
     * @param usuario
     * @param id
     * @param telefono
     * @param nombre
     * @param rol
     */
    public ResponseLogin(Integer id, String nombre, String usuario, String carnet, String correo, String telefono, String password, Integer idRol, String rol, Integer idEstado, String estado, String fechaCreacion) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.carnet = carnet;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.idRol = idRol;
        this.rol = rol;
        this.idEstado = idEstado;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}