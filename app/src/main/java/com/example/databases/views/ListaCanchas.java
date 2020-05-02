package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.databases.NavigationDrawer;
import com.example.databases.R;
import com.example.databases.adapters.CanchasAdapter;

import com.example.databases.db.ContratoReservas;
import com.example.databases.db.CrudCanchas;

import com.example.databases.model.Cancha;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaCanchas extends AppCompatActivity {
    Button btnSalir;
    ListView listView;
    FloatingActionButton fabAgregarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canchas);
        btnSalir=findViewById(R.id.btnSalir);
        listView = findViewById(R.id.lvUsuarios);
        fabAgregarUsuario =  findViewById(R.id.fabAgregarUsuario );

        final CrudCanchas crudCanchas =  new CrudCanchas(getApplicationContext());

        final ArrayList<Cancha> canchas = crudCanchas.listarCanchas();

        CanchasAdapter canchasAdapter =  new CanchasAdapter(getApplicationContext() ,R.layout.item_usuario , canchas );


        listView.setAdapter(canchasAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( getApplicationContext() , FormularioCanchas.class  );
                i.putExtra(ContratoReservas.TablaCancha.idCancha , String.valueOf(canchas.get(position).getIdCancha()) );
                startActivity(i);

            }
        });

        fabAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , FormularioCanchas.class  );
                startActivity(i);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , NavigationDrawer.class  );
                startActivity(i);
                finish();
            }
        });

    }
}
