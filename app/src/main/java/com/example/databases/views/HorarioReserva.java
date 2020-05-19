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
import com.example.databases.api.reservas.RequestReserva;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.Duis;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
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
    private int idCancha;
    private String fecha;
    private int idTipoReserva;
    private String duiUsuario;
    private ResponseLogin usuarioActual;
    private int idHorario;
    private AlertDialog.Builder builder;
    private String title="Seleccionar horario";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_reserva);
        lvHorarios= findViewById(R.id.lvHorarios);
        retrofitInit();

        Intent i = getIntent();
        fecha  = i.getStringExtra("fecha" );
        idCancha =  Integer.parseInt(i.getStringExtra("idCancha" )) ;
        idTipoReserva  = Integer.parseInt(i.getStringExtra("idTipo")) ;
        duiUsuario = i.getStringExtra("dui");
        usuarioActual = Session.obtenerSessionUsuario(getApplicationContext());

        getSupportActionBar().setTitle(title);

        listarHorarios(fecha , idCancha  );


        builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseas realizar la reserva");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "Reserva realizada satisfactoriamente", Toast.LENGTH_SHORT).show();
                realizarReserva();
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

                if(horarioArrayList.get(position).getEstado().equals("OCUPADO")){
                    Toast.makeText(getApplicationContext(), "Horario no disponible", Toast.LENGTH_SHORT).show();
                }else{
                    idHorario = horarioArrayList.get(position).getHorario();
                    builder.setMessage("Tu reserva sera realizada para el dia "+fecha+" a las "+horarioArrayList.get(position).getHoraInicio()+" horas"  );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private  void listarHorarios(final String fecha , int idCancha){
        Call<JsonElement> listarHorarios =reservasCanchasService.listarHorariosDisponibles(fecha ,String.valueOf(idCancha));

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
                         HorariosAdapter  horariosAdapter  = new HorariosAdapter( getApplicationContext() , R.layout.item_horario , horarioArrayList  , fecha );
                         lvHorarios.setAdapter(horariosAdapter);
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Toast.makeText(getApplicationContext() ,   t.getMessage() , Toast.LENGTH_SHORT ).show();
            }
        });
    }


    private void realizarReserva(){

        Call<JsonElement> realizarReserva;
        //Verificar el nivel de usuario el cual realiza la solicitud
        if(usuarioActual.getIdRol() == 3){ //Usuario final
            realizarReserva= reservasCanchasService.ingresarReserva(fecha ,  null,  usuarioActual.getId()  , null , idHorario , idCancha , idTipoReserva );
        }else{ //Usuarios administradores y asistentes
            realizarReserva= reservasCanchasService.ingresarReserva(fecha , usuarioActual.getId() , null  , duiUsuario , idHorario , idCancha , idTipoReserva );
        }

        realizarReserva.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){
                    //Toast.makeText(getApplicationContext(), "Reserva realizada con exito", Toast.LENGTH_SHORT).show();
                    String jsonString  = response.body().toString();
                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);

                    boolean redireccionar = false;

                    //Si la reserva se realizo con exito
                    if(errorObject.getMensaje().equals("Reservacion registrada por Asistente") || errorObject.getMensaje().equals("Reservacion registrada por usuario")){
                        redireccionar = true;
                    }
                    Toast.makeText(getApplicationContext() , errorObject.getMensaje() , Toast.LENGTH_LONG).show();

                    if(redireccionar){
                        Intent intent = new Intent( getApplicationContext() , ListaReservas.class  );
                        startActivity(intent);
                        finish();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean existsError(String jsonString){
        if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
            errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
            Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
