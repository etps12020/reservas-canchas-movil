package com.example.databases.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoReservaAdapter;
import com.example.databases.adapters.SpinnerCanchaAdapter;
import com.example.databases.api.canchas.Cancha;
import com.example.databases.api.reservas.EstadoReserva;
import com.example.databases.api.reservas.RequestUpdateReserva;
import com.example.databases.api.reservas.Reserva;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleReserva extends AppCompatActivity {

    private EditText edtNombre ,  edtNumeroReservacion , edtFechaHora , comentarioCancenlacion;
    private Spinner spnCancha , spnEstadosReservacion;
    private Button btnActualizar;
    private Reserva reservaSeleccionada ;
    private ErrorObject errorObject;
    private ResponseLogin usuarioLogin;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;

    private ArrayList<Cancha> canchaArrayList;
    private ArrayList<EstadoReserva> estadoReservaArrayList;
    private String title="Detalle reserva";
    private AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva);
        edtNombre =  findViewById(R.id.edtNombre);
        edtNumeroReservacion =  findViewById(R.id.edtNumReserva);
        edtFechaHora =  findViewById(R.id.edtFecha);
        comentarioCancenlacion =  findViewById(R.id.comentarioCancenlacion);
        btnActualizar =  findViewById(R.id.btnActualizar);

        spnCancha =  findViewById(R.id.spnCancha);
        spnEstadosReservacion =  findViewById(R.id.spnEstadosReservacion);

        retrofitInit();

        getSupportActionBar().setTitle(title);


        Intent i  =  getIntent();
        final String  jsonReserva =     i.getStringExtra("infoReserva");
        Gson gson =  new Gson();

        reservaSeleccionada =  gson.fromJson(  jsonReserva ,  Reserva.class  );
        usuarioLogin = Session.obtenerSessionUsuario(getApplicationContext());

        edtNombre.setText( reservaSeleccionada.getNombreCompleto() );
        edtNumeroReservacion.setText( String.valueOf(reservaSeleccionada.getNumReservacion())   );
        edtFechaHora.setText(  reservaSeleccionada.getFechaReservacion()+" "+reservaSeleccionada.getHoraInicio()    );

        listarEstadosReservaciones();
        listarCanchas();

        builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=  new Intent( getApplicationContext() , ListaReservas.class);
                startActivity(i);
                finish();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  String comentario =  comentarioCancenlacion.getText().toString();
                  int idEstadoReserva =  estadoReservaArrayList.get(spnEstadosReservacion.getSelectedItemPosition()).getId();


                  if(idEstadoReserva == 5  && comentario.isEmpty() ){ //Si la reserva fue cancelada pedir comnetario de justificacion
                    comentarioCancenlacion.setError("Campo requerido");
                  }else {
                      RequestUpdateReserva requestUpdateReserva = new RequestUpdateReserva(reservaSeleccionada.getNumReservacion(),
                              usuarioLogin.getId(), idEstadoReserva, comentario);

                      Call<JsonElement> actualizar =  reservasCanchasService.actualizarReservacion(requestUpdateReserva);


                      actualizar.enqueue(new Callback<JsonElement>() {
                          @Override
                          public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                              if(response.isSuccessful()){
                                  String jsonString  = response.body().toString();

                                  if(jsonString.contains("mensaje")){
                                      ErrorObject errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                                      builder.setMessage(errorObject.getMensaje());
                                      AlertDialog alertDialog = builder.create();
                                      alertDialog.show();
                                  }
                              }
                          }

                          @Override
                          public void onFailure(Call<JsonElement> call, Throwable t) {

                          }
                      });



                  }


            }
        });

    }

    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }


    private void listarCanchas(){

        Call<JsonElement> listarCanchas =  reservasCanchasService.listarCachas();

        listarCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    if(response.isSuccessful()){
                        String jsonString  = response.body().toString();
                        if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                            errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                            Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                        }else{
                            canchaArrayList = new Gson().fromJson(jsonString , new TypeToken<List<Cancha>>(){}.getType() );

                            if(canchaArrayList.size()>0){
                                SpinnerCanchaAdapter spinnerCanchaAdapter =  new SpinnerCanchaAdapter( getApplicationContext() ,  R.layout.support_simple_spinner_dropdown_item ,  canchaArrayList  );
                                spnCancha.setAdapter(spinnerCanchaAdapter);
                                int indexCancha = findIndexCancha(reservaSeleccionada.getIdCancha());
                                spnCancha.setSelection(  indexCancha);
                            }
                        }
                    }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }
    private void listarEstadosReservaciones(){


        Call<JsonElement> listarEstadosReservas;
        if(usuarioLogin.getIdRol()==3){ //Usuario comun
            listarEstadosReservas =  reservasCanchasService.listarEstadosReservacionUsuarioFinal("4");
        }else{ //Administradores
            listarEstadosReservas =  reservasCanchasService.listarEstadosReservacion();
        }



        listarEstadosReservas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();
                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        estadoReservaArrayList = new Gson().fromJson(jsonString , new TypeToken<List<EstadoReserva>>(){}.getType() );

                        if(estadoReservaArrayList.size()>0){
                            EstadoReservaAdapter estadoReservaAdapter =  new EstadoReservaAdapter(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item  ,   estadoReservaArrayList );;
                            spnEstadosReservacion.setAdapter(estadoReservaAdapter);
                            int indexEstadoReserva = findIndexEstadoReserva(reservaSeleccionada.getIdEstado());
                            spnEstadosReservacion.setSelection( indexEstadoReserva);
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private int findIndexCancha(int idCancha){
        int index = 0;
        for(Cancha cancha: canchaArrayList){
            if(cancha.getCancha() == idCancha){
                return index;
            }else{
                index++;
            }
        }
        return -1;
    }

    private int findIndexEstadoReserva(int idEstadoReserva ){
        int index = 0;
        for(EstadoReserva estadoReserva: estadoReservaArrayList){
            if(estadoReserva.getId() == idEstadoReserva){
                return index;
            }else{
                index++;
            }
        }

        return -1;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(  getApplicationContext() , ListaReservas.class  );
        startActivity(intent);
        finish();
    }
}

