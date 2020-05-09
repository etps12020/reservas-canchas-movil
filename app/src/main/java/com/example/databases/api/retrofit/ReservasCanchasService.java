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
    @GET("login.php")
    @Headers("Content-Type: application/json")
    Call<JsonElement> Login(@Query("usuario")   String usuario  , @Query("password") String password  );
}
