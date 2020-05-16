package com.example.databases.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.databases.R;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.Session;

public class PerfilFragment extends Fragment {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private EditText edtNombre ,  edtCorreo  , edtTelefono  , edtContrasena , edtConfirmarContrasena;
    private Button btnActualizarPerfil;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_perfil, container, false);

        edtNombre =  view.findViewById(R.id.edtNombre);
        edtCorreo =  view.findViewById(R.id.edtCorreo);
        edtTelefono =  view.findViewById(R.id.edtDui);
        edtContrasena =  view.findViewById(R.id.edtPassword);
        edtConfirmarContrasena =  view.findViewById(R.id.edtConfirmPassword);
        btnActualizarPerfil =  view.findViewById(R.id.btnActualizarPerfil);

        retrofitInit();

        ResponseLogin userLogin = Session.obtenerSessionUsuario(getActivity());
        if(userLogin!=null){ //Si el usuario se encuentra dentro de las preferencias
            edtNombre.setText(userLogin.getNombre());
            edtCorreo.setText(userLogin.getCorreo());
            edtTelefono.setText(userLogin.getTelefono());
            edtContrasena.setText( userLogin.getPassword());
            edtConfirmarContrasena.setText(  userLogin.getPassword());
        }

        btnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return view;
    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void actualizarInformacionUsuario(){

         //Hacer la solictud de actualizacio  del usuario

            //Mostrar mensaje segun  la transaccion  sea exitosa o error
            //Volver a guardar la nueva informacion en las shard preferences para persistencia de session
    }
}
