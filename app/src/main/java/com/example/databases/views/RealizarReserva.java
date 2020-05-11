package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.MainActivity;
import com.example.databases.R;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealizarReserva extends AppCompatActivity {

    ArrayList<ResponseLogin> usuariosList;
    ArrayList<Object> canchas;
    ArrayList<Object> tiposCanchas;
    ArrayList<Object> edificios;
    ArrayList<String>  duisUsuarios  , nombresCanchas ,  nombresTiposCanchas , nombresEdificios;
    CalendarView calendarViewReserva;
    Spinner spnUsuarios , spnTipoCancha , spnCanchas;
    SpinnerDialog spinnerDialogUsuarios , spinnerDialogTipoCancha  , spinnerDialogCanchas;
    EditText edtUsuario;
    Long date;
    String fecha= "2020-05-08"; //Fecha quemada
    String idCancha ="2"; //Cancha quemada
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject errorObject=  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_reserva);

        calendarViewReserva  =  findViewById(R.id.calendarViewReserva);
        spnUsuarios =  findViewById(R.id.spnUsuarios);
        spnTipoCancha =  findViewById(R.id.spnTipoCancha);
        spnCanchas =  findViewById(R.id.spnCanchas);
        edtUsuario =  findViewById(R.id.edtUsuario);
        duisUsuarios  = new ArrayList<>();
        nombresCanchas = new ArrayList<>();
        nombresTiposCanchas =  new ArrayList<>();
        nombresEdificios =  new ArrayList<>();

        retrofitInit();

        edtUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(duisUsuarios.size()==0){//Si esta vacio los duis que los llene
                    cargarUsuarios();
                }else{
                    spinnerDialogUsuarios.showSpinerDialog();
                }


            }
        });


        spinnerDialogUsuarios = new SpinnerDialog( RealizarReserva.this , duisUsuarios , "Seleccione un usuario"  );
        spinnerDialogUsuarios.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                Toast.makeText(getApplicationContext(), "Selected"+ s, Toast.LENGTH_SHORT).show();
            }
        });


        calendarViewReserva.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Intent i = new  Intent(  getApplicationContext() , HorarioReserva.class  );
                i.putExtra("idCancha" , idCancha);
                i.putExtra("fecha" , fecha );
                startActivity(i);
            }
        });


    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void cargarUsuarios(){

        final Call<JsonElement>  listarUsuarios   =       reservasCanchasService.listarUsuarios();

        listarUsuarios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if( response.isSuccessful()  ){

                    String jsonString  = response.body().toString();

                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        usuariosList = new Gson().fromJson(jsonString , new TypeToken<List<ResponseLogin>>(){}.getType() );
                        if(usuariosList.size()>0){
                            for(int i=0;  i<usuariosList.size() ; i++){
                                duisUsuarios.add(  usuariosList.get(i).getDui()  );
                            }
                            spinnerDialogUsuarios.showSpinerDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
