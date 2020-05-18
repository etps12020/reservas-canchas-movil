package com.example.databases.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoUsuarioAdapter;
import com.example.databases.adapters.RolUsuarioAdapter;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.roles.Rol;
import com.example.databases.api.usuarios.EstadoUsuario;
import com.example.databases.api.usuarios.RequestUpdateUser;
import com.example.databases.api.usuarios.RequestUpdateUserAsistente;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
import com.example.databases.model.RolUsuario;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarUsuario extends AppCompatActivity {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private EditText edtNombre ,edtDui  , edtCarnet , edtCorreo , edtTelefono  , edtContrasenia , edtConfirmacionContrasenia;
    private Spinner spnRol , spnEstado;
    private ErrorObject errorObject;
    private ArrayList<Rol> rolesUsuariosList;
    private ArrayList<EstadoUsuario> estadoUsuariosList;
    private ArrayList<ResponseLogin> responseLoginList;
    private ResponseLogin  usuarioSeleccionado;
    private ResponseLogin  usuarioLogin;
    private RequestUpdateUser requestUpdateUser;
    private RequestUpdateUserAsistente requestUpdateUserAsistente;
    private Button btnActualizar;
    int estadoUsuario ,  rolUsuario;
    private AlertDialog.Builder builder;
    private String title ="Actualizar Usuarios";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
        estadoUsuariosList =  new ArrayList<>();
        rolesUsuariosList =  new ArrayList<>();

        edtNombre   = findViewById(R.id.edtNombre   );
        edtDui  = findViewById(R.id.edtDui  );
        edtCarnet = findViewById(R.id.edtCarnet );
        edtCorreo = findViewById(R.id.edtCorreo );
        edtTelefono = findViewById(R.id.edtTelefono );
        edtContrasenia =  findViewById(R.id.edtContrasena);
        edtConfirmacionContrasenia =  findViewById(R.id.edtConfirmacionContrasenia);

        btnActualizar =  findViewById(R.id.btnActualizar);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        spnRol =  findViewById(R.id.spnRol);
        spnEstado =  findViewById(R.id.spnEstado);


        builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i =  new Intent(  getApplicationContext() ,  ListaUsuarios.class  );
                startActivity(i);
                finish();
            }
        });




        retrofitInit();
        final Intent i = getIntent();
        obtenerInformacionUsuario(i.getStringExtra("id"));

        usuarioLogin = Session.obtenerSessionUsuario(getApplicationContext());


        if(usuarioLogin.getIdRol()==2){
            edtNombre.setEnabled(false);
            edtDui.setEnabled(false);
            edtCorreo.setEnabled(false);
            edtCarnet.setEnabled(false);
            spnEstado.setEnabled(false);
            spnRol.setEnabled(false);
        }

        spnRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idUsuario  =  Integer.parseInt(i.getStringExtra("id"))  ;
                String nombre =  edtNombre.getText().toString();
                String dui =  edtDui.getText().toString();
                String correo =  edtCorreo.getText().toString();
                String carnet =  edtCarnet.getText().toString();
                String telefono  = edtTelefono.getText().toString();
                String contrasenia = edtContrasenia.getText().toString();
                String confirmacionConltrasenia =edtConfirmacionContrasenia.getText().toString();

                if(nombre.isEmpty()){
                    edtNombre.setError("El campo nombre es requerido");
                }else if(correo.isEmpty()){
                    edtDui.setError("El campo correo es requerido");
                }else if(dui.isEmpty()){
                    edtDui.setError("El campo dui es requerido");
                }else if(carnet.isEmpty()){
                    edtCarnet.setError("El campo carnet es requerido");
                }else if(telefono.isEmpty()){
                    edtTelefono.setError("El campo telefono es requerido");
                } else if(!contrasenia.equals(confirmacionConltrasenia)){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else{
                    if(usuarioLogin.getIdRol()==1){
                        //Actualizar usuario por administrador
                        requestUpdateUser = new RequestUpdateUser( usuarioLogin.getId()  , idUsuario ,dui , nombre ,correo , carnet , telefono , contrasenia , rolUsuario ,estadoUsuario  );
                        Call<JsonElement> actualizarUsuario =  reservasCanchasService.actualizarUsuario(requestUpdateUser);

                        actualizarUsuario.enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                if(response.isSuccessful()){
                                    String jsonString  = response.body().toString();
                                    if(jsonString.contains("mensaje")){ //Mensaje en caso de falta de datos
                                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                                        builder.setMessage(errorObject.getMensaje());
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });


                    }else if(usuarioLogin.getIdRol()==2){

                        requestUpdateUserAsistente =  new RequestUpdateUserAsistente(idUsuario , telefono, contrasenia);
                        Call<JsonElement> actualizarUsuario =  reservasCanchasService.actualizarUsuario(requestUpdateUserAsistente);

                        actualizarUsuario.enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                String jsonString  = response.body().toString();
                                if(jsonString.contains("mensaje")){ //Mensaje en caso de falta de datos
                                    errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                                    builder.setMessage(errorObject.getMensaje());
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            }
                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {

                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "No tienes permisos para actualizar desde esta pantalla", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void obtenerInformacionUsuario(String idUsuario){

        Call<JsonElement> buscarUsuario  = reservasCanchasService.obtenerUsuario(idUsuario , "buscar");

        buscarUsuario.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(response.isSuccessful()){

                    String jsonString  = response.body().toString();

                    if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        responseLoginList = new Gson().fromJson(jsonString , new TypeToken<List<ResponseLogin>>(){}.getType() );
                        if(responseLoginList.size()>=1){  //Login
                            Gson  gson =  new Gson();
                            usuarioSeleccionado = responseLoginList.get(0); //Establece la informacion del usuario

                            edtDui.setText( usuarioSeleccionado.getDui() );
                            edtNombre.setText( usuarioSeleccionado.getNombre()  );
                            edtCarnet.setText( usuarioSeleccionado.getCarnet()  );
                            edtCorreo.setText( usuarioSeleccionado.getCorreo());
                            edtTelefono.setText( usuarioSeleccionado.getTelefono()   );
                            edtContrasenia.setText(  usuarioSeleccionado.getPassword()  );
                            edtConfirmacionContrasenia.setText( usuarioSeleccionado.getPassword()   );

                            rolUsuario =  usuarioSeleccionado.getIdRol();
                            estadoUsuario =  usuarioSeleccionado.getIdEstado();
                            obtenerRolesUsuarios();
                            obtenerEstadosUsuarios();



                        }

                    }



                }



            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });



    }

    //Obtener lista de roles de usuario
    private void obtenerRolesUsuarios(){
        Call<JsonElement> rolesUsuarios    =  reservasCanchasService.listarRolesUsuario();

        rolesUsuarios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();
                    if(jsonString.contains("mensaje")){ //Mensaje en caso de falta de datos
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        //Lista de roles de usuario
                        rolesUsuariosList = new Gson().fromJson(jsonString , new TypeToken<ArrayList<Rol>>(){}.getType() );
                        RolUsuarioAdapter rolUsuarioAdapter = new RolUsuarioAdapter(getApplicationContext(), R.layout.custom_simple_spinner_item , rolesUsuariosList );
                        spnRol.setAdapter(rolUsuarioAdapter);

                        int indexRol = findIndexOfRol( usuarioSeleccionado.getIdRol() );
                        spnRol.setSelection(indexRol);

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "Error en la comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Obtener lista de estados de usuarios
    private void obtenerEstadosUsuarios(){
        final Call<JsonElement>  estadosUsuarios =  reservasCanchasService.listarEstadosUsuarios();

        estadosUsuarios.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String jsonString  = response.body().toString();
                if(response.isSuccessful()){
                    if(jsonString.contains("mensaje")){ //Mensaje en caso de falta de datos
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                    }else{
                        estadoUsuariosList = new Gson().fromJson(jsonString , new TypeToken<List<EstadoUsuario>>(){}.getType() );
                        EstadoUsuarioAdapter estadoUsuarioAdapter = new EstadoUsuarioAdapter(getApplicationContext() , R.layout.custom_simple_spinner_item , estadoUsuariosList );
                        spnEstado.setAdapter(estadoUsuarioAdapter);

                        int indexEstado =  findIndexOfEstado( usuarioSeleccionado.getIdEstado() );
                        spnEstado.setSelection(indexEstado);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "Error en la comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Inicializacion de retrofit
    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private int findIndexOfEstado(int idEstado){
        int index=0;
        for (EstadoUsuario estadoUsuario: estadoUsuariosList  ) {
            if(estadoUsuario.getId()==idEstado){
                return index;
            }else{
                index++;
            }
        }
        return -1;
    }
    
    private int findIndexOfRol(int idRol){
        int index = 0;
        for(Rol rolUsuario: rolesUsuariosList){
            if(rolUsuario.getId()==idRol){
                return index;
            }else{
                index++;
            }
        }
        return  -1;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext() , ListaUsuarios.class);
        startActivity(intent);
        finish();
    }
}
