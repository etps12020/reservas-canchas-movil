package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databases.db.CrudUsuarios;
import com.example.databases.model.Usuario;
import com.example.databases.views.AdministradorHome;
import com.example.databases.views.AsistenteHome;
import com.example.databases.views.UsuarioHome;

public class MainActivity extends AppCompatActivity {


    private EditText edtUsuario , edtContrasena;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();//Oculta el appbar

        btnLogin =  findViewById(R.id.btnLogin);
        edtUsuario =  findViewById(R.id.edtUsuario);
        edtContrasena =  findViewById(R.id.edtContrasena);


        //Aqui hace uso de la clase crud usuarios el cual ya inicializa la base de datos
        final CrudUsuarios crudUsuarios =  new CrudUsuarios(getApplicationContext()); //Obtiene instancia de la clase

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user =  edtUsuario.getText().toString();
                String password =  edtContrasena.getText().toString();

                Usuario  usuario = new Usuario();

                usuario.setUsuario(user);
                usuario.setPassword(password);


                //Verifica login en la base
                usuario = crudUsuarios.login(usuario);

                //Si el usuario es null el usuario no es valido
                if(usuario!=null){

                    if(usuario.getEstadoUsuario().getIdEstado()==1){
                        Toast.makeText( getApplicationContext() , "Bienvenido "+usuario.getNombreCompleto() , Toast.LENGTH_SHORT   ).show();

                        Intent intent ;
                        if(usuario.getRolUsuario().getIdRolUsuario()==1){
                            intent =  new Intent(getApplicationContext() , NavigationDrawer.class);
                        }else if(usuario.getRolUsuario().getIdRolUsuario()==2){
                            intent =  new Intent(getApplicationContext() , AsistenteHome.class);
                        }else{
                            intent =  new Intent(getApplicationContext() , UsuarioHome.class);
                        }
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(  getApplicationContext(), "Usuario actualmente inactivo", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Credenciales Invalidas", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }




}
