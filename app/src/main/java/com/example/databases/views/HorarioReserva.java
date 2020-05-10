package com.example.databases.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        final String fecha  = i.getStringExtra("fecha" );
        final String idCancha = i.getStringExtra("idCancha" );

        listarHorarios(i.getStringExtra("fecha" ) , i.getStringExtra("idCancha" )  );

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseas realizar la reserva");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Reserva realizada satisfactoriamente", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        lvHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                builder.setMessage("Tu reserva sera realizada para el dia "+fecha+" a las "+horarioArrayList.get(position).getHoraInicio()+" horas"  );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
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
