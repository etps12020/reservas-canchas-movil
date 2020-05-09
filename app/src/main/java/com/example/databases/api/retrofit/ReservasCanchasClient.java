package com.example.databases.api.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.databases.api.utilidades.Constantes;

public class ReservasCanchasClient {

    private static ReservasCanchasClient instance =  null;
    private ReservasCanchasService reservasCanchasService;
    private Retrofit retrofit;

    public ReservasCanchasClient(){
        retrofit  =  new Retrofit.Builder()
                .baseUrl(Constantes.API_RESERVAS_CANCHAS_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reservasCanchasService = retrofit.create(ReservasCanchasService.class);
    }

    public static ReservasCanchasClient getInstance(){
        if(instance==null){
            instance =  new ReservasCanchasClient();
        }
        return  instance;
    }

    public ReservasCanchasService getReservasCanchasService(){
        return reservasCanchasService;
    }

}
