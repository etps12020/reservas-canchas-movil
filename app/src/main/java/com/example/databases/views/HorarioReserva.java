package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.HorariosAdapter;
import com.example.databases.api.reservas.Horario;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioReserva extends AppCompatActivity {

    private ListView lvHorarios;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject errorObject;
    private ArrayList<Horario> horarioArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_reserva);
        lvHorarios= findViewById(R.id.lvHorarios);
        retrofitInit();

        Intent i = getIntent();

        Toast.makeText(getApplicationContext() , i.getStringExtra("fecha" )+" " +i.getStringExtra("idCancha" ) , Toast.LENGTH_SHORT ).show();
        listarHorarios(i.getStringExtra("fecha" ) , i.getStringExtra("idCancha" )  );
    }

    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private  void listarHorarios(String fecha ,String idCancha){
        Call<JsonElement> listarHorarios =reservasCanchasService.listarHorariosDisponibles(fecha ,idCancha);

        listarHorarios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();
                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                         horarioArrayList= new Gson().fromJson(jsonString , new TypeToken<ArrayList<Horario>>(){}.getType() );
                         HorariosAdapter  horariosAdapter  = new HorariosAdapter( getApplicationContext() , R.layout.item_horario , horarioArrayList  );
                         lvHorarios.setAdapter(horariosAdapter);
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }
}
