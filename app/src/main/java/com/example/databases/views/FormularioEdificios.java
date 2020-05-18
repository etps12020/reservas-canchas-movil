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
import android.text.TextUtils;
import android.util.Base64;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoEdificioAdapter;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.db.CrudEdificio;
import com.example.databases.db.CrudEstadoEdificio;
import com.example.databases.model.Edificio;
import com.example.databases.model.EstadoEdificio;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioEdificios extends AppCompatActivity {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private TextView edtNombreEdificio , edtDireccion , edtDescripcion;
    private Spinner spnEstado;
    private Button btnAccionEdificio;
    private ImageView imvEdificio;
    private String imagenEdificio;
    private String title="Ingresar Edificio";
    private AlertDialog.Builder builder;
    private ErrorObject errorObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_edificios);

        edtNombreEdificio =  findViewById(R.id.edtNombreEdificio);
        edtDireccion =  findViewById(R.id.edtDireccion);
        edtDescripcion =  findViewById(R.id.edtDescripcion);
        btnAccionEdificio =  findViewById(R.id.btnAccionEdificios);
        imvEdificio =  findViewById(R.id.imvEdificio);


        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

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





        retrofitInit();

        imvEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        btnAccionEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String nombreEdificio =  edtNombreEdificio.getText().toString();
                 String direccion  =  edtDireccion.getText().toString();
                 String descripcion  =  edtDescripcion.getText().toString();


                 if(nombreEdificio.isEmpty()){
                   edtNombreEdificio.setError("Campo requerido");
                 } else if(direccion.isEmpty()){
                     edtDireccion.setError("Canpo requerido");
                 }else if(descripcion.isEmpty()){
                     edtDescripcion.setError("Campo requerido" );
                 }else if(imagenEdificio.isEmpty()){
                     Toast.makeText(getApplicationContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show();
                 }else{
                     Call<JsonElement> ingresarCancha = reservasCanchasService.ingresarEdifcio(nombreEdificio , direccion ,  descripcion , imagenEdificio);;

                     ingresarCancha.enqueue(new Callback<JsonElement>() {
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
                             Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                         }
                     });
                 }





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
//            imvEdificio.setImageURI(path);
            try {
                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , path);
                imagenEdificio = convertirImagenString(bitmap);
//
//
//                byte[] decodedString = Base64.decode(imagenEdificio, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                 imvEdificio.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void retrofitInit() {
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService = reservasCanchasClient.getReservasCanchasService();
    }

    private String convertirImagenString(Bitmap bitmap){
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
