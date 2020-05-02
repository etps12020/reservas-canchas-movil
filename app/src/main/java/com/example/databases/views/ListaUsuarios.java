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
import com.example.databases.adapters.UsuariosAdapter;
import com.example.databases.db.ContratoReservas;
import com.example.databases.db.CrudUsuarios;
import com.example.databases.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {

Button btnSalir;
    ListView listView;
    FloatingActionButton fabAgregarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnSalir=findViewById(R.id.btnSalir);
        listView = findViewById(R.id.lvUsuarios);
        fabAgregarUsuario =  findViewById(R.id.fabAgregarUsuario );

        final CrudUsuarios crudUsuarios =  new CrudUsuarios(getApplicationContext());

        final ArrayList<Usuario>  usuarios = crudUsuarios.listarUsuarios();

        UsuariosAdapter usuariosAdapter =  new UsuariosAdapter(getApplicationContext() ,R.layout.item_usuario , usuarios );


        listView.setAdapter(usuariosAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent( getApplicationContext() , FormularioUsuarios.class  );
                i.putExtra(ContratoReservas.TablaUsuario.idusuario , String.valueOf(usuarios.get(position).getIdUsuario()) );
                startActivity(i);

            }
        });

        fabAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , FormularioUsuarios.class  );
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
