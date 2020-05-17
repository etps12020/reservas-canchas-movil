package com.example.databases.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.databases.R;
import com.example.databases.adapters.EdificioAdapter;
import com.example.databases.adapters.EstadoCanchaAdapter;
import com.example.databases.adapters.TipoCanchaAdapter;
import com.example.databases.api.horarios.Horario;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.example.databases.api.edificios.Edificio;
import com.example.databases.api.canchas.TipoCancha;
import com.example.databases.api.utilidades.ErrorObject;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioCanchas extends AppCompatActivity {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private Spinner spinner, spinner2,spinner3;
    private SpinnerDialog spndHoraInicio ,  spndHoraFin;

    private ArrayList<Horario> horarioArrayList;
    private ArrayList<Edificio> edificioArrayList;
    private ArrayList<TipoCancha> tipoCanchaArrayList;
    private ArrayList<String> horarios;
    private ErrorObject errorObject;

    ImageView  imvCanha;
    TextView edtNombre , edtDescripcion  , edtTelefono , edtHoraInicio ,  edtHoraFin ;
    Spinner spnTipoCancha ,  spnEdificio;
    Button btnCrear;
    int idTipoCancha , idEdificioCancha , idHoraInicio , idHoraFin;
    String imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_canchas);

        imvCanha =  findViewById(R.id.imvCancha);
        edtNombre= findViewById(R.id.edtNombre);
        edtDescripcion= findViewById(R.id.edtDescripcion);
        edtTelefono= findViewById(R.id.edtTelefono);
        edtHoraInicio= findViewById(R.id.edtHoraInicio);
        edtHoraFin= findViewById(R.id.edtHoraFin);
        spnEdificio= findViewById(R.id.spnEdificio);
        spnTipoCancha= findViewById(R.id.spnTipoCancha);
        btnCrear   =  findViewById(R.id.btnCrear);

        horarios =  new ArrayList<>();
        retrofitInit();

        cargarTiposCanchas();
        cargarEdificios();
        cargarHoras();


        spndHoraInicio =  new SpinnerDialog(FormularioCanchas.this , horarios , "Seleccione una hora inicio");
        spndHoraInicio.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String hora, int i) {
                Toast.makeText(getApplicationContext(), ""+i, Toast.LENGTH_SHORT).show();
                edtHoraInicio.setText(hora);
            }
        });

        spndHoraFin =  new SpinnerDialog(FormularioCanchas.this , horarios , "Seleccione una hora fin");
        spndHoraFin.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String hora, int i) {
                Toast.makeText(getApplicationContext(), ""+i, Toast.LENGTH_SHORT).show();
                edtHoraFin.setText(hora);
            }
        });

        edtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spndHoraInicio.showSpinerDialog();
            }
        });

        edtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spndHoraFin.showSpinerDialog();

            }
        });

        imvCanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        //Ingreso de la cancha
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre =  edtNombre.getText().toString();
                String descripcion =  edtDescripcion.getText().toString();
                String telefono  =  edtTelefono.getText().toString();

                if(nombre.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo nombre es requerido", Toast.LENGTH_SHORT).show();
                }else if(descripcion.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo descripcion es requerido", Toast.LENGTH_SHORT).show();
                }else if(telefono.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El campo telefono es requirido", Toast.LENGTH_SHORT).show();
                }else{
                    //Ingresar cancha
                    Call<JsonElement> ingresarCancha = reservasCanchasService.ingresarCanchas(
                            nombre ,
                            descripcion,
                            telefono,
                            idHoraInicio,
                            idHoraFin,
                            idEdificioCancha,
                            idTipoCancha,
                            imagen
                    );

                    ingresarCancha.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            String jsonString  = response.body().toString();

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


    private void cargarEdificios(){

        Call<JsonElement> listarEdificios =  reservasCanchasService.listarEdificios();

        listarEdificios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();

                if(!existsError(jsonString)){ //Si no hay errores en la peticion
                    edificioArrayList = new Gson().fromJson(jsonString , new TypeToken<List<Edificio>>(){}.getType() );

                    EdificioAdapter edificioAdapter =  new EdificioAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, edificioArrayList);

                    spnEdificio.setAdapter(edificioAdapter);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }

    private void cargarTiposCanchas(){

        Call<JsonElement> listarTiposCanchas = reservasCanchasService.listarTiposCanchas();

        listarTiposCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();

                if(!existsError(jsonString)){ //Si no hay errores en la peticion

                    tipoCanchaArrayList  = new Gson().fromJson(jsonString , new TypeToken<List<TipoCancha>>(){}.getType() );
                    TipoCanchaAdapter tipoCanchaAdapter =  new TipoCanchaAdapter(getApplicationContext() ,R.layout.support_simple_spinner_dropdown_item , tipoCanchaArrayList);
                    spnTipoCancha.setAdapter(tipoCanchaAdapter);
                }



            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void cargarHoras(){


        Call<JsonElement> listarHorariosCanchas  =  reservasCanchasService.listarHorariosCanchas();

        listarHorariosCanchas.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String jsonString  = response.body().toString();

                if(!existsError(jsonString)){ //Si no hay errores en la peticion
                    horarioArrayList = new Gson().fromJson(jsonString , new TypeToken<List<Horario>>(){}.getType() );

                    for (Horario horario: horarioArrayList){
                        horarios.add(horario.getHoraInicio()+" - "+horario.getHoraFin());
                    }

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


    private  void cargarImagen(){
        Intent intent  =  new Intent( Intent.ACTION_PICK ,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI   );
        intent.setType("image/");
        startActivityForResult( intent.createChooser(intent , "Seleccionee la aplicacion ")  ,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Uri path =  data.getData();
            imvCanha.setImageURI(path);
        }
    }


    private boolean existsError(String jsonResponse){
        if(jsonResponse.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
            errorObject =  new Gson().fromJson(jsonResponse , ErrorObject.class);
            Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}

