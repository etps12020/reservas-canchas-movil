package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.databases.NavigationDrawer;
import com.example.databases.R;
import com.example.databases.adapters.UsuariosAdapter;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.db.ContratoReservas;
import com.example.databases.db.CrudUsuarios;
import com.example.databases.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaUsuarios extends AppCompatActivity {

    private Button btnSalir;
    private ListView listView;
    private FloatingActionButton fabAgregarUsuario;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject errorObject;
    private ArrayList<ResponseLogin> responseLoginList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnSalir=findViewById(R.id.btnSalir);
        listView = findViewById(R.id.lvUsuarios);
        fabAgregarUsuario =  findViewById(R.id.fabAgregarUsuario );
        //Inicializacion de Retrofit
        retrofitInit();
        //Peticion para listar usuarios
        listarUsuarios();
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( getApplicationContext() , FormularioUsuarios.class  );
                i.putExtra(ContratoReservas.TablaUsuario.idusuario , String.valueOf(responseLoginList.get(position).getId()) );
                startActivity(i);

            }
        });

        fabAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , FormularioUsuarios.class  );
                startActivity(i);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , NavigationDrawer.class  );
                startActivity(i);
                finish();
            }
        });

    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }


    private void listarUsuarios(){


        Call<JsonElement> listarUsuariosApi    =  reservasCanchasService.listarUsuarios();

        listarUsuariosApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String jsonString  = response.body().toString();

                if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                    Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                }else{
                    responseLoginList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<ResponseLogin>>(){}.getType() );

                    if(responseLoginList.size()>=1){  //Generar Listado de usuarios
                        UsuariosAdapter usuariosAdapter =  new UsuariosAdapter(getApplicationContext() ,R.layout.item_usuario , responseLoginList );
                        listView.setAdapter(usuariosAdapter);
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
