package com.example.reservas.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservas.R;

public class AdministradorHome extends AppCompatActivity {

    ImageView imvUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_home);

        imvUsuarios =  findViewById(R.id.imvUsuarios);

        imvUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i =  new Intent(getApplicationContext() , ListaUsuarios.class);
                startActivity(i);
            }
        });

    }
}
