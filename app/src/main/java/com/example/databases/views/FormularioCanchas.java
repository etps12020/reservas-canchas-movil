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
import com.example.databases.db.ContratoReservas;
import com.example.databases.db.CrudCanchas;
import com.example.databases.db.CrudEdificio;
import com.example.databases.db.CrudEstadoCancha;

import com.example.databases.db.CrudTipoCancha;

import com.example.databases.model.Cancha;
import com.example.databases.model.Edificio;
import com.example.databases.model.EstadoCancha;

import com.example.databases.model.TipoCancha;


import java.util.ArrayList;

public class FormularioCanchas extends AppCompatActivity {
    private Spinner spinner, spinner2,spinner3;
    EditText edtNombre, edtDescripcion , edtHoraInicio , edtHoraFin , edtFechaCreacion , edtTelefonoContacto  ;
    TextView tvIndicacionImagen;
    Button btnCrear;
    Cancha cancha=new Cancha();
    Button btnSalir;
    EstadoCancha estadoCancha;
    Edificio edificio;
    TipoCancha tipoCancha;
    ImageView  imvCanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_canchas);


        final Intent i =  getIntent();
        btnSalir=findViewById(R.id.btnSalir);
        edtNombre = findViewById(R.id.edtNombre);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtTelefonoContacto = findViewById(R.id.edtTelefonoContacto);
        edtHoraInicio= findViewById(R.id.edtHoraInicio);
        edtHoraFin = findViewById(R.id.edtHoraFin);
        edtFechaCreacion = findViewById(R.id.edtFechaCreacion);
        imvCanha =  findViewById(R.id.imvCancha);
        tvIndicacionImagen =  findViewById(R.id.tvIndicacionImagen);

        btnCrear =  findViewById(R.id.btnCrear);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);


        final CrudCanchas crudCanchas =  new CrudCanchas(getApplicationContext());
        final CrudEstadoCancha crudEstadoCancha = new CrudEstadoCancha(getApplicationContext());
        final CrudEdificio crudEdificio =  new CrudEdificio(getApplicationContext());
        final CrudTipoCancha crudTipoCancha =  new CrudTipoCancha(getApplicationContext());
        final ArrayList<EstadoCancha> estadoCanchaArrayList =  crudEstadoCancha.listarEstadosCanchas();
        final ArrayList<Edificio> edificioArrayList =  crudEdificio.listarEdificios();
        final ArrayList<TipoCancha> tipoCanchaArrayList =  crudTipoCancha.listarTiposCancha();

        final EstadoCanchaAdapter estadoCanchaAdapter = new EstadoCanchaAdapter(getApplicationContext() , R.layout.custom_simple_spinner_item ,  estadoCanchaArrayList   );
        final TipoCanchaAdapter tipoCanchaAdapter = new TipoCanchaAdapter(getApplicationContext(), R.layout.custom_simple_spinner_item , tipoCanchaArrayList );
        final EdificioAdapter edificioAdapter = new EdificioAdapter(getApplicationContext(), R.layout.custom_simple_spinner_item , edificioArrayList );
        spinner.setAdapter(estadoCanchaAdapter);
        spinner2.setAdapter(tipoCanchaAdapter);
        spinner3.setAdapter(edificioAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estadoCancha = estadoCanchaArrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCancha = tipoCanchaArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edificio = edificioArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(i.hasExtra(ContratoReservas.TablaCancha.idCancha)){
            String id = i.getStringExtra(ContratoReservas.TablaCancha.idCancha);
            cancha = crudCanchas.obtenerCancha( Integer.parseInt(id));
            //Toast.makeText(getApplicationContext(),   usuario.getUsuario()  , Toast.LENGTH_SHORT).show();
            edtNombre.setText(  cancha.getNombre()   );
            edtDescripcion.setText( cancha.getDescripcion());
            edtTelefonoContacto.setText(cancha.getTelefonoContacto());
            edtHoraInicio.setText(  cancha.getHoraInicio()  );
            edtHoraFin.setText(cancha.getHoraFin());
            edtFechaCreacion.setText(cancha.getFechaCreacion());

            btnCrear.setText("Actualizar");

            estadoCancha = cancha.getEstadoCancha();
            tipoCancha =  cancha.getTipoCancha();
            edificio =  cancha.getEdificio();
            spinner.setSelection( (estadoCancha.getIdEstado() - 1));
            spinner2.setSelection(  (tipoCancha.getIdTipoCancha()  -1)  );
            spinner3.setSelection(  (edificio.getIdEdificio()  -1)  );
        }

        imvCanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edtNombre.getText().toString();
                String descripcion = edtDescripcion.getText().toString();
                String telefonocontacto =  edtTelefonoContacto.getText().toString();
                String horainicio =  edtHoraInicio.getText().toString();
                String horafin =  edtHoraFin.getText().toString();
                String fecha  =  edtFechaCreacion.getText().toString();

                if(TextUtils.isEmpty(descripcion)){
                    edtDescripcion.setError("Campo requerido");
                }else if(TextUtils.isEmpty(telefonocontacto)){
                    edtTelefonoContacto.setError("Campo requerido");
                }else if(TextUtils.isEmpty(horainicio)){
                    edtHoraInicio.setError("Campo requerido");
                }else if(TextUtils.isEmpty(horafin)){
                    edtHoraFin.setError("Campo requerido");
                }else if(TextUtils.isEmpty(fecha)){
                    edtFechaCreacion.setError("Campo requerido");
                }else{

                    //usuario.setUsuario(user);
                    cancha.setNombre(nombre  );
                    cancha.setDescripcion(descripcion);
                    cancha.setTelefonoContacto(telefonocontacto);
                    cancha.setHoraInicio(horainicio);
                    cancha.setHoraFin(horafin);
                    cancha.setFechaCreacion(fecha);
                    cancha.setEstadoCancha(estadoCancha);
                    cancha.setTipoCancha(tipoCancha);
                    cancha.setEdificio(edificio);

                    cancha.setNombre(nombre);

                    if(i.hasExtra(ContratoReservas.TablaCancha.idCancha)){
                        cancha.setIdCancha(  Integer.parseInt(i.getStringExtra(ContratoReservas.TablaCancha.idCancha))  );


                        crudCanchas.editarCacha(cancha);
                        Toast.makeText(FormularioCanchas.this, "Cancha actualizada correctamente", Toast.LENGTH_SHORT).show();
                    }else{

                        crudCanchas.agregarCancha(cancha);
                        Toast.makeText(FormularioCanchas.this, "Cancha creado correctamente", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() ,ListaCanchas.class  );
                startActivity(i);
                finish();
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
}

