package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.databases.MainActivity;
import com.example.databases.R;

public class AdministradorHome extends AppCompatActivity {

    ImageView imvUsuarios,imvCanchas , imvEdificios;

    Button btnSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_home);
        btnSalir=findViewById(R.id.btnSalir);
        imvUsuarios =  findViewById(R.id.imvUsuarios);
        imvCanchas =  findViewById(R.id.imvCanchas);
        imvEdificios = findViewById(R.id.imvEdificios);
        imvUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i =  new Intent(getApplicationContext() , ListaUsuarios.class);
                startActivity(i);
            }
        });
        imvCanchas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i =  new Intent(getApplicationContext() , ListaCanchas.class);
                startActivity(i);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() , MainActivity.class  );
                startActivity(i);

            }
        });
        imvEdificios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  =  new Intent( getApplicationContext() , ListaEdificios.class  );
                startActivity(i);

            }
        });
    }
}
