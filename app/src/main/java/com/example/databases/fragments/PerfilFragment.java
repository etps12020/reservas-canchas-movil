package com.example.databases.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.usuarios.RequestUpdateUserAsistente;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private EditText edtNombre ,  edtCorreo  , edtTelefono  , edtContrasena , edtConfirmarContrasena;
    private Button btnActualizarPerfil;
    private ResponseLogin userLogin;
    private AlertDialog.Builder builder;
    private ErrorObject errorObject;
    private ArrayList<ResponseLogin> responseLoginList;

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

        builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizacion de datos");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buscarUsuario();//Vuelve a llenar la session
            }
        });




        userLogin = Session.obtenerSessionUsuario(getActivity());
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
                String telefono = edtTelefono.getText().toString();
                String password =edtContrasena.getText().toString();
                String confirmacion  = edtConfirmarContrasena.getText().toString();

                if(telefono.isEmpty()){
                    edtTelefono.setError("El campo telefono es requerido");
                }else if(password.isEmpty()){
                    edtContrasena.setError("El campo de contraseña es requerido");
                }else if(confirmacion.isEmpty()){
                    edtConfirmarContrasena.setError("Debees confirmar tu contraseña");
                }else if(!confirmacion.equals(password)){
                    Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else{
                    actualizarInformacionUsuario(telefono , password);
                }

            }
        });


        return view;
    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void actualizarInformacionUsuario(String telefono , String password){

        RequestUpdateUserAsistente requestUpdateUserAsistente =  new RequestUpdateUserAsistente(userLogin.getId() ,  telefono , password);
         //Hacer la solictud de actualizacio  del usuario
        Call<JsonElement> actualizarUsuario =  reservasCanchasService.actualizarUsuario(requestUpdateUserAsistente);

        actualizarUsuario.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();

                if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
//                    Toast.makeText(getActivity(), jsonString, Toast.LENGTH_SHORT).show();
                    builder.setMessage(errorObject.getMensaje());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

            //Mostrar mensaje segun  la transaccion  sea exitosa o error
            //Volver a guardar la nueva informacion en las shard preferences para persistencia de session
    }

    private void buscarUsuario(){

        String accion="buscar";
        String idUsuario = String.valueOf(userLogin.getId()) ;

        Call<JsonElement> buscarInfoUsuario = reservasCanchasService.listarUsuarios(idUsuario, accion);

        buscarInfoUsuario.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();
                if(!jsonString.contains("mensaje")) { //Mensajes de Usuario inactivo o usuario no existe
                    responseLoginList = new Gson().fromJson(jsonString, new TypeToken<List<ResponseLogin>>() {
                    }.getType());


                    if (responseLoginList.size() >= 1) {  //Login

                        Gson gson = new Gson();
                        ResponseLogin responseLogin = responseLoginList.get(0);
                        String strUserJson = gson.toJson(responseLogin);  //Convirtiendo en JSON el usuario

                        Session.crearSessionUsuario(strUserJson, getActivity());

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });



    }




}
