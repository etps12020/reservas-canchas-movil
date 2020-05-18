package com.example.databases.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.api.utilidades.Session;
import com.example.databases.db.ContratoReservas;
import com.example.databases.api.usuarios.EstadoUsuario;
import com.example.databases.api.roles.Rol;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class FormularioUsuarios extends AppCompatActivity {

    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private Spinner spinner, spinner2;
    private EditText edtNombre, edtDui, edtCarnet, edtCorreo, edtTelefono;

    private Button btnCrear;
    private Button btnSalir;
    private EstadoUsuario estadoUsuario ;
    private Rol  rolUsuario;
    ErrorObject errorObject;
    ArrayList<Rol> rolesUsuariosList;
    ArrayList<EstadoUsuario> estadoUsuariosList;
    List<ResponseLogin> responseLoginList;
    private ResponseLogin usuario;
    private ResponseLogin userLogin;
    private AlertDialog.Builder builder;
    private String title="Ingresar usuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuarios);

        final Intent i =  getIntent();

        final EditText edtNombre =  findViewById(R.id.edtNombre);
        final EditText edtDui =  findViewById(R.id.edtDui);
        final EditText edtCarnet =  findViewById(R.id.edtCarnet);
        final EditText edtCorreo =  findViewById(R.id.edtCorreo);
        final EditText edtTelefono =  findViewById(R.id.edtTelefono);

        btnCrear =  findViewById(R.id.btnCrear);
        spinner = (Spinner)findViewById(R.id.spinner);

        userLogin = Session.obtenerSessionUsuario(getApplicationContext());


        retrofitInit();  //Inicializa las clases de retrofit
        obtenerRolesUsuarios(); //Obtiene  y renderiza la lista de roles de usuario

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=  new Intent( getApplicationContext() , ListaUsuarios.class);
                startActivity(i);
                finish();
            }
        });


        if(i.hasExtra(ContratoReservas.TablaUsuario.idusuario)){
            String id = i.getStringExtra(ContratoReservas.TablaUsuario.idusuario);
            obtenerInformacionUsuario(id); //Solicitar informacion del usuario
            //Seleccion del rol y estado de usuario en base a datos de usuario
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String dui  = edtDui.getText().toString();
                String nombreCompleto = edtNombre.getText().toString();
                String carnet =  edtCarnet.getText().toString();
                String correo =  edtCorreo.getText().toString();
                String telefono  =  edtTelefono.getText().toString();



                 if(TextUtils.isEmpty(nombreCompleto)){
                    edtNombre.setError("Campo requerido");
                }else if(TextUtils.isEmpty(dui)){
                    edtDui.setError("Campo requerido");
                }
                 else if(TextUtils.isEmpty(carnet)){
                    edtCarnet.setError("Campo requerido");
                }else if(TextUtils.isEmpty(correo)){
                    edtCorreo.setError("Campo requerido");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches() ){
                    edtCorreo.setError("Correo no valido");
                 }else if(TextUtils.isEmpty(telefono)){
                    edtTelefono.setError("Campo requerido");
                }else{


                     int idRolUsuarioIngresar = rolesUsuariosList.get((int) spinner.getSelectedItemId()).getId();
                     int idRolUsuarioLogin  = userLogin.getIdRol();

                     if(idRolUsuarioIngresar==1 &&  idRolUsuarioLogin==2    ){
                         builder.setMessage("No puedes ingresar usuarios con el rol seleccionado");
                         AlertDialog alertDialog = builder.create();
                         alertDialog.show();
                     }else{


                         Call<JsonElement> ingresarUsuario  = reservasCanchasService.ingresarUsuario(
                                 nombreCompleto ,
                                 dui  ,
                                 carnet ,
                                 correo ,
                                 telefono ,
                                 idRolUsuarioIngresar
                         );

                         ingresarUsuario.enqueue(new Callback<JsonElement>() {
                             @Override
                             public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                 if(response.isSuccessful()){
                                     String jsonString  = response.body().toString();

                                     if(jsonString.contains("mensaje")){ //Mensajes de Usuario inactivo o usuario no existe
                                         errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                                         Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();
                                     }else{
                                         responseLoginList = new Gson().fromJson(jsonString , new TypeToken<List<ResponseLogin>>(){}.getType() );

                                         if(responseLoginList.size() > 0 ){
                                             ResponseLogin usuarioIngresado =  responseLoginList.get(0);


                                             builder.setMessage("Usuario creado exitosamente!!"+"\nUsuario: "+usuarioIngresado.getUsuario()+"\nPassword: "+usuarioIngresado.getPassword()+"\nFecha Creacion: "+usuarioIngresado.getFechaCreacion());
                                             AlertDialog alertDialog = builder.create();
                                             alertDialog.show();
                                         }


                                     }



                                 }




                             }

                             @Override
                             public void onFailure(Call<JsonElement> call, Throwable t) {
                                 Toast.makeText(getApplicationContext(),  t.getMessage().toString()    , Toast.LENGTH_SHORT).show();
                             }
                         });

                     }


                }
            }
        });


    }

    private void obtenerInformacionUsuario(String idusuario){


        Call<JsonElement> infoUsuario =  reservasCanchasService.obtenerUsuario(idusuario , "buscar");

        infoUsuario.enqueue(new Callback<JsonElement>() {
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
                             usuario = responseLoginList.get(0); //Establece la informacion del usuario
                            mostrarInformacionUsuario();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(),  t.getMessage()  , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarInformacionUsuario(){
//        edtUsuario.setText(  usuario.getUsuario()   );
//        edtNombre.setText( usuario.getNombre());
//        edtCarnet.setText(usuario.getCarnet());
//        edtCorreo.setText(  usuario.getCorreo()  );
//        edtTelefono.setText(usuario.getTelefono());
//        edtFecha.setText(usuario.getFechaCreacion());
//        edtContrasena.setText( usuario.getPassword()  );
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
                        spinner.setAdapter(rolUsuarioAdapter);

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
                        spinner2.setAdapter(estadoUsuarioAdapter);
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

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext() , ListaUsuarios.class);
        startActivity(intent);
        finish();

    }
}
