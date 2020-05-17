package com.example.databases.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.example.databases.db.CrudEdificio;
import com.example.databases.db.CrudEstadoEdificio;
import com.example.databases.model.Edificio;
import com.example.databases.model.EstadoEdificio;

import java.util.ArrayList;

public class FormularioEdificios extends AppCompatActivity {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private TextView edtNombreEdificio , edtDireccion , edtDescripcion;
    private Spinner spnEstado;
    private Button btnAccionEdificio;
    private ImageView imvEdificio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_edificios);

        edtNombreEdificio =  findViewById(R.id.edtNombreEdificio);
        edtDireccion =  findViewById(R.id.edtDireccion);
        edtDescripcion =  findViewById(R.id.edtDescripcion);
        btnAccionEdificio =  findViewById(R.id.btnAccionEdificios);
        imvEdificio =  findViewById(R.id.imvEdificio);

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
            imvEdificio.setImageURI(path);
        }
    }

    private void retrofitInit() {
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService = reservasCanchasClient.getReservasCanchasService();
    }
}
