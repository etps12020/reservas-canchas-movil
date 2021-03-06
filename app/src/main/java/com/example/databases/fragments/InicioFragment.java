package com.example.databases.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.Session;
import com.example.databases.views.ListaCanchas;
import com.example.databases.views.ListaEdificios;
import com.example.databases.views.ListaReservas;
import com.example.databases.views.ListaUsuarios;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment implements View.OnClickListener {

    LinearLayout layoutUsuarios,  layoutCanchas , layoutReservas, layoutEdificios;
    TextView textUserName;
    ResponseLogin userLogin;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_inicio, container, false);

        userLogin = Session.obtenerSessionUsuario(getActivity());

        layoutUsuarios =  view.findViewById(R.id.layoutUsuarios);
        layoutCanchas =  view.findViewById(R.id.layoutCanchas);
        layoutEdificios =  view.findViewById(R.id.layoutEdificios);
        layoutReservas =  view.findViewById(R.id.layoutReservas);
        textUserName =  view.findViewById(R.id.textUserName);

        layoutUsuarios.setOnClickListener(this);
        layoutCanchas.setOnClickListener(this);
        layoutEdificios.setOnClickListener(this);
        layoutReservas.setOnClickListener(this);


        if(userLogin.getIdRol()==1){

        }else if(userLogin.getIdRol()==2){
            layoutCanchas.setVisibility(LinearLayout.INVISIBLE);
            layoutEdificios.setVisibility(LinearLayout.INVISIBLE);
        }else{
            layoutCanchas.setVisibility(LinearLayout.INVISIBLE);
            layoutEdificios.setVisibility(LinearLayout.INVISIBLE);
            layoutUsuarios.setVisibility(LinearLayout.INVISIBLE);
        }


        textUserName.setText(userLogin.getNombre());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.layoutUsuarios:
                Intent usuarios = new Intent( getActivity() ,   ListaUsuarios.class);
                startActivity(usuarios);
                getActivity().finish();
                break;
            case R.id.layoutCanchas:
                Intent canchas = new Intent( getActivity() ,   ListaCanchas.class);
                startActivity(canchas);
                getActivity().finish();
                break;

            case R.id.layoutEdificios:
                Intent edificios = new Intent( getActivity() ,   ListaEdificios.class);
                startActivity(edificios);
                getActivity().finish();
                break;

            case R.id.layoutReservas:
                Intent reservas = new Intent( getActivity() ,   ListaReservas.class);
                startActivity(reservas);
                getActivity().finish();
                break;
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
