package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoEdificioAdapter;
import com.example.databases.db.CrudEdificio;
import com.example.databases.db.CrudEstadoEdificio;
import com.example.databases.model.Edificio;
import com.example.databases.model.EstadoEdificio;

import java.util.ArrayList;

public class FormularioEdificios extends AppCompatActivity {

    TextView edtNombreEdificio , edtDireccion , edtDescripcion;
    Spinner spnEstado;
    Button btnAccionEdificio;
    Edificio edificio;
    EstadoEdificio estadoEdificio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_edificios);


        CrudEstadoEdificio crudEstadoEdificio =  new CrudEstadoEdificio(getApplicationContext());
        final CrudEdificio crudEdificio =  new CrudEdificio(getApplicationContext());

        final ArrayList<EstadoEdificio> estadosEdificios =  crudEstadoEdificio.listarEstadosEdificicios();


        edtNombreEdificio =  findViewById(R.id.edtNombreEdificio);
        edtDireccion =  findViewById(R.id.edtDireccion);
        edtDescripcion =  findViewById(R.id.edtDescripcion);
        spnEstado =  findViewById(R.id.spnEstadoEdificio);
        btnAccionEdificio =  findViewById(R.id.btnAccionEdificios);

        EstadoEdificioAdapter estadoEdificioAdapter =  new EstadoEdificioAdapter(getApplicationContext() ,  R.layout.custom_simple_spinner_item , estadosEdificios   );

        spnEstado.setAdapter(estadoEdificioAdapter);


        spnEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoEdificio = estadosEdificios.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Intent i = getIntent();

        edificio  =  new Edificio();
        if(i.hasExtra("id")){
            edificio =  crudEdificio.obtenerEdificio(i.getIntExtra("id" , 0));
            edtNombreEdificio.setText(edificio.getNombre());
            edtDescripcion.setText( edificio.getDescripcion());
            edtDireccion.setText( edificio.getDireccion());
            estadoEdificio =  edificio.getEstadoEdificio();
            spnEstado.setSelection( (estadoEdificio.getIdEstado() - 1));
        }



        btnAccionEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreEdificio = edtNombreEdificio.getText().toString();
                String descripcion  =  edtDescripcion.getText().toString();
                String direccion  =  edtDireccion.getText().toString();

                if(TextUtils.isEmpty(nombreEdificio)){
                    edtNombreEdificio.setError("Campo requerido!");
                }else if(TextUtils.isEmpty(descripcion)){
                    edtDescripcion.setError("Campo requerido!");
                }else if(TextUtils.isEmpty(direccion)){
                    edtDireccion.setError("Campo requerido!");
                }else{
                    edificio.setNombre(nombreEdificio);
                    edificio.setDireccion(direccion);
                    edificio.setDescripcion(descripcion);
                    edificio.setEstadoEdificio(estadoEdificio);

                    if(i.hasExtra("id")){
                        crudEdificio.editarEdificio(edificio);
                        Toast.makeText(getApplicationContext(), "Actualizado correctamente" , Toast.LENGTH_SHORT).show();
                    }else{
                        crudEdificio.agregarEdificio(edificio);
                        Toast.makeText(getApplicationContext(), "Ingresado correctamente" , Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });





    }
}
