package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.databases.R;
import com.example.databases.adapters.EdificiosAdapter;
import com.example.databases.db.CrudEdificio;
import com.example.databases.model.Edificio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaEdificios extends AppCompatActivity {

    ListView listaEdificios;
    FloatingActionButton fabAgregarEdificio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);

        listaEdificios =  findViewById(R.id.listEdificios);
        fabAgregarEdificio =  findViewById(R.id.fabAgregarEdificio);


        CrudEdificio crudEdificio =  new CrudEdificio(getApplicationContext());

        final ArrayList<Edificio> edificios=  crudEdificio.listarEdificios();

        EdificiosAdapter edificiosAdapter =  new EdificiosAdapter(getApplicationContext() , R.layout.cardview_edificios , edificios);

        listaEdificios.setAdapter(edificiosAdapter);

        listaEdificios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Edificio edificio = edificios.get(position);


                Intent i =  new Intent(getApplicationContext() ,  FormularioEdificios.class);
                i.putExtra("id" , edificio.getIdEdificio());
                startActivity(i);

            }
        });

        fabAgregarEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(getApplicationContext() ,  FormularioEdificios.class);
                startActivity(i);

            }
        });


    }
}
