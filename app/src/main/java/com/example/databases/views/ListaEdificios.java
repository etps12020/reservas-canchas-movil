package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.databases.NavigationDrawer;
import com.example.databases.R;
import com.example.databases.adapters.EdificiosAdapter;
//import com.example.databases.db.CrudEdificio;
//import com.example.databases.model.Edificio;

import com.example.databases.adapters.UsuariosAdapter;
import com.example.databases.api.usuarios.ResponseLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseEdificio;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.db.ContratoReservas;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaEdificios extends AppCompatActivity {

    ListView listaEdificios;
    FloatingActionButton fabAgregarEdificio;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject errorObject;
    private ArrayList<ResponseEdificio> responseEdificioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);

        listaEdificios =  findViewById(R.id.listEdificios);
        fabAgregarEdificio =  findViewById(R.id.fabAgregarEdificio);
        //Inicializacion de Retrofit
        retrofitInit();
        //Peticion para listar usuarios
        listarEdificios();

        listaEdificios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( getApplicationContext() , ActualizarEdificio.class  );
                i.putExtra("id" , String.valueOf(responseEdificioList.get(position).getId()) );
                startActivity(i);

            }
        });

        fabAgregarEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , FormularioEdificios.class  );
                startActivity(i);
            }
        });

    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }


    private void listarEdificios(){


        Call<JsonElement> listarEdificiosApi    =  reservasCanchasService.listarEdificios();

        listarEdificiosApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String jsonString  = response.body().toString();

                if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                    Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                }else{
                    responseEdificioList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<ResponseEdificio>>(){}.getType() );

                    if(responseEdificioList.size()>=1){  //Generar Listado de usuarios
                        EdificiosAdapter edificiosAdapter =  new EdificiosAdapter(getApplicationContext() ,R.layout.item_edificios , responseEdificioList);
                        listaEdificios.setAdapter(edificiosAdapter);
                    }


                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de comunicación con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
