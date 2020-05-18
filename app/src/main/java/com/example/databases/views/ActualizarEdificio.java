package com.example.databases.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoEdificioAdapter;
import com.example.databases.api.edifcios.RequestUpdateEdificio;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.roles.Rol;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.usuarios.EstadoEdificio;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.example.databases.api.edificios.Edificio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarEdificio extends AppCompatActivity {


    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private EditText edtNombreEdificio , edtDireccion , edtDescripcion  ;
    private Spinner spnEstadoEdificio;
    private ImageView imvEdificio;
    private Button btnAccionEdificios;
    private ErrorObject errorObject;
    private ArrayList<EstadoEdificio> estadoEdificioArrayList;
    private Edificio  edificioSeleccionado;
    private String imagenEdificio;
    private int idEstadEdificio;
    private String title="Actualizar Edificio";
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_edificio);

        edtNombreEdificio =  findViewById(R.id.edtNombreEdificio);
        edtDireccion  = findViewById(R.id.edtDireccion);
        edtDescripcion  =  findViewById(R.id.edtDescripcion);
        spnEstadoEdificio =  findViewById(R.id.spnEstadoEdificio);
        btnAccionEdificios =  findViewById(R.id.btnAccionEdificios);
        imvEdificio =  findViewById(R.id.imvEdificio);
        retrofitInit();

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i =  new Intent(  getApplicationContext() ,  ListaEdificios.class  );
                startActivity(i);
                finish();
            }
        });

        final Intent i  = getIntent();
        obtenerDatosEdificio(  i.getStringExtra("id")  );

        imvEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             cargarImagen();
            }
        });

        btnAccionEdificios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreEdificio =  edtNombreEdificio.getText().toString();
                String direccion =  edtDireccion.getText().toString();
                String descripcion   = edtDescripcion.getText().toString();

                if(nombreEdificio.isEmpty()){
                    edtNombreEdificio.setError("Campo requerido");
                }else if(direccion.isEmpty()){
                    edtDireccion.setError("Campo requerido");
                }else if(descripcion.isEmpty()){
                    edtDescripcion.setError("Campo requerido");
                }else if(imagenEdificio.isEmpty()){
                    Toast.makeText(ActualizarEdificio.this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
                }else{

                    RequestUpdateEdificio requestUpdateEdificio  =  new RequestUpdateEdificio(
                            Integer.parseInt(i.getStringExtra("id")) ,
                            nombreEdificio ,
                            direccion ,
                            descripcion ,
                            idEstadEdificio ,
                            imagenEdificio
                    );

                    Call<JsonElement> actualizarEdificio =  reservasCanchasService.actualizarEdificio(requestUpdateEdificio);

                    actualizarEdificio.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            if(response.isSuccessful()){
                                String jsonString  = response.body().toString();
                                if(jsonString.contains("mensaje")){ //Mensaje en caso de falta de datos
                                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
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

    private void obtenerDatosEdificio(String id){

        Call<JsonElement> obtenerEdificio =  reservasCanchasService.obtenerEdificio(id);

        obtenerEdificio.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();
                if(response.isSuccessful()){
                    if (jsonString.contains("mensaje")) { //Mensaje en caso de falta de datos
                        errorObject = new Gson().fromJson(jsonString, ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        ArrayList<Edificio> edificioArrayList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<Edificio>>(){}.getType() );

                        if(edificioArrayList.size() > 0){
                            edificioSeleccionado  =  edificioArrayList.get(0);
                            edtNombreEdificio.setText(edificioSeleccionado.getNombre());
                            edtDescripcion.setText(edificioSeleccionado.getDescripcion());
                            edtDireccion.setText(edificioSeleccionado.getDireccion());
                            String base64Image = edificioSeleccionado.getImagen();
                            imagenEdificio = edificioSeleccionado.getImagen();
                            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imvEdificio.setImageBitmap(decodedByte);
                            cargarEstadosEstadosEdificios();
                        }

                    }

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    //Inicializacion de retrofit
    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void cargarEstadosEstadosEdificios() {
        Call<JsonElement> listarEstadosEdificios =  reservasCanchasService.listarEstadosEdificios();

        listarEstadosEdificios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();

                if(response.isSuccessful()) {
                    if (jsonString.contains("mensaje")) { //Mensaje en caso de falta de datos
                        errorObject = new Gson().fromJson(jsonString, ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                         estadoEdificioArrayList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<EstadoEdificio>>(){}.getType() );
                        EstadoEdificioAdapter estadoEdificioAdapter =  new EstadoEdificioAdapter( getApplicationContext() , R.layout.support_simple_spinner_dropdown_item , estadoEdificioArrayList);
                        spnEstadoEdificio.setAdapter(estadoEdificioAdapter);
                        int indexEstadoEdificio  = findIndexEstadoUsuario(edificioSeleccionado.getIdEstado());
                        idEstadEdificio =  edificioSeleccionado.getIdEstado();
                        spnEstadoEdificio.setSelection(indexEstadoEdificio);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private int findIndexEstadoUsuario(int idEstadoEdificio){
        int index = 0;
        for( EstadoEdificio estadoEdificio :  estadoEdificioArrayList  ){
            if(estadoEdificio.getId()  == idEstadoEdificio){
                return index;
            }else{
                index++;
            }
        }
        return -1;
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
            imvEdificio.setImageURI(path);
            try {
                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , path);
                imagenEdificio = convertirImgString(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertirImgString(Bitmap bitmap){
        ByteArrayOutputStream array =  new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG ,  100 , array   );
        byte[] imagenByte =  array.toByteArray();
        String imagenString = Base64.encodeToString(  imagenByte , Base64.DEFAULT );
        return imagenString;
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(getApplicationContext() , ListaEdificios.class);
        startActivity(intent);
        finish();
    }
}
