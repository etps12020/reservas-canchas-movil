package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databases.api.retrofit.ResponseApi;
import com.example.databases.api.usuarios.RequestLogin;
import com.example.databases.api.usuarios.ResponseLogin;
import com.example.databases.api.utilidades.ErrorObject;
import com.example.databases.db.CrudUsuarios;
import com.example.databases.model.Usuario;
import com.example.databases.views.AdministradorHome;
import com.example.databases.views.AsistenteHome;
import com.example.databases.views.UsuarioHome;
import com.example.databases.api.retrofit.ReservasCanchasService;
import com.example.databases.api.retrofit.ReservasCanchasClient;
import com.example.databases.api.utilidades.Net;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private EditText edtUsuario , edtContrasena;
    private Button btnLogin;
    private ReservasCanchasService reservasCanchasService;
    private ReservasCanchasClient reservasCanchasClient;
    private ErrorObject  errorObject=  null;
    private List<ResponseLogin> responseLoginList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();//Oculta el appbar

        btnLogin =  findViewById(R.id.btnLogin);
        edtUsuario =  findViewById(R.id.edtUsuario);
        edtContrasena =  findViewById(R.id.edtContrasena);

        final Net net =  new Net(getApplicationContext());
        //Aqui hace uso de la clase crud usuarios el cual ya inicializa la base de datos
        final CrudUsuarios crudUsuarios =  new CrudUsuarios(getApplicationContext()); //Obtiene instancia de la clase



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user =  edtUsuario.getText().toString();
                String password =  edtContrasena.getText().toString();

                retrofitInit(); //Creacion de instancias de libreria

                if(net.comprobarRed()){
                    Login(user, password);
                }else{
                    Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
                }



                //Si el usuario es null el usuario no es valido
//                if(usuario!=null){
//
//                    if(usuario.getEstadoUsuario().getIdEstado()==1){
//                        Toast.makeText( getApplicationContext() , "Bienvenido "+usuario.getNombreCompleto() , Toast.LENGTH_SHORT   ).show();
//
//                                 Intent intent ;
//                        if(usuario.getRolUsuario().getIdRolUsuario()==1){
//                            intent =  new Intent(getApplicationContext() , NavigationDrawer.class);
//                        }else if(usuario.getRolUsuario().getIdRolUsuario()==2){
//                            intent =  new Intent(getApplicationContext() , AsistenteHome.class);
//                        }else{
//                            intent =  new Intent(getApplicationContext() , UsuarioHome.class);
//                        }
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        Toast.makeText(  getApplicationContext(), "Usuario actualmente inactivo", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//
//                    Toast.makeText(getApplicationContext(), "Credenciales Invalidas", Toast.LENGTH_SHORT).show();
//                }


            }
        });

    }


    private void retrofitInit(){
        reservasCanchasClient = ReservasCanchasClient.getInstance();
        reservasCanchasService =  reservasCanchasClient.getReservasCanchasService();
    }

    private void Login(String usuario ,  String contrasena){

        Call<JsonElement>  login    =  reservasCanchasService.Login( usuario , contrasena   );

        /*
        login.enqueue(new Callback<List<ResponseLogin>>() {

            @Override
            public void onResponse(Call<List<ResponseLogin>> call, Response<List<ResponseLogin>> response) {

                for(int i = 0 ; i < response.body().size()  ; i++){
                    Toast.makeText( getApplicationContext()  ,  response.body().get(i).getCorreo() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ResponseLogin>> call, Throwable t) {

            }
        });
         */

        login.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    String jsonString  = response.body().toString();

                    if(jsonString.contains("mensaje")){ //Errores
                        errorObject =  new Gson().fromJson(jsonString , ErrorObject.class);
                        Toast.makeText(getApplicationContext(), errorObject.getMensaje(), Toast.LENGTH_SHORT).show();

                    }else{
                        responseLoginList = new Gson().fromJson(jsonString , new TypeToken<List<ResponseLogin>>(){}.getType() );


                        if(responseLoginList.size()>=1){

                            Toast.makeText(getApplicationContext(),  "Bienvenido "+responseLoginList.get(0).getNombre()  , Toast.LENGTH_SHORT).show();

                            Intent intent =  new Intent(getApplicationContext() , NavigationDrawer.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de comunicacion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });


    }




}
