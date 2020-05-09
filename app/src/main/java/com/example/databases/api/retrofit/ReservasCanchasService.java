package com.example.databases.api.retrofit;

import com.example.databases.api.usuarios.RequestLogin;
import com.example.databases.api.usuarios.ResponseLogin;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReservasCanchasService {

    /*RUTAS PARA MANEJO DE USUARIOS*/
    @GET("login.php") //Login
    @Headers("Content-Type: application/json")
    Call<JsonElement> Login(@Query("usuario")   String usuario  , @Query("password") String password  );

    @GET("usuario.php") //Obtener usuario especifico
    @Headers("Content-Type: application/json")
    Call<JsonElement> obtenerUsuario(@Query("id")   String id );

    @GET("usuario.php") //Listar usuarios existentes
    @Headers("Content-Type: application/json")
    Call<JsonElement> listarUsuarios();

    @GET("rolUsuario.php") //Listar todos los roles de los usuarios
    @Headers("Content-Type: application/json")
    Call<JsonElement> listarRolesUsuario();

    @GET("estadoUsuario.php") //Listar todos los roles de los usuarios
    @Headers("Content-Type: application/json")
    Call<JsonElement> listarEstadosUsuarios();

}
