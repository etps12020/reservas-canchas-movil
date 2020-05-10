package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.ReservasAdapter;
import com.example.databases.api.reservas.Reserva;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaReservas extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ListView lvReservas;
    private ArrayList<Reserva> reservaArrayList;
    private ErrorObject errorObject;
    private FloatingActionButton fabAgregarReserva;
    private FloatingActionButton fabFiltroFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        lvReservas =  findViewById(R.id.lvReservas);
        fabAgregarReserva =  findViewById(R.id.fabAgregarReserva);
        fabFiltroFecha =  findViewById(R.id.fabFiltroFecha);
        lvReservas.setFocusable(false);


        retrofitInit(); //Iniciaalizacion de retrofit
        listarReservas();

        lvReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext() , "Clicked" , Toast.LENGTH_SHORT).show();
            }
        });

        //Crear cuadro de dialogo con calendario
        fabFiltroFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker  = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager() , "date picker");

            }
        });

        //Agregar nueva reserva
        fabAgregarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , RealizarReserva.class );
                startActivity(i);
            }
        });


    }

    private void listarReservas(){

        String usuario = "2";
        String fecha="";
        String numReservacion = "";
        String cancha = "";
        Call<JsonElement> listarReservas =reservasCanchasService.listarReservas( usuario , fecha ,  numReservacion ,  cancha  );

        listarReservas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();



                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        reservaArrayList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<Reserva>>(){}.getType() );
                        ReservasAdapter reservasAdapter = new ReservasAdapter(getApplicationContext() , R.layout.item_list_reserva ,  reservaArrayList );

                        lvReservas.setAdapter(reservasAdapter);



                    }

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error al comunicarse con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR ,  year);
        calendar.set(Calendar.MONTH , month);
        calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);

        String fechaActual  = DateFormat.getInstance().format(calendar.getTime());

        //Consultar nuevamente el listado y poner la fecha actual por defecto
        Toast.makeText(getApplicationContext(), "Change the list of reserves for day "+fechaActual, Toast.LENGTH_SHORT).show();
    }
}
