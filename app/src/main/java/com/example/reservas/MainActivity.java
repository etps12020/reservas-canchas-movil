package com.example.reservas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import conexion.DataBaseOpenHelper;

public class MainActivity extends AppCompatActivity {
    private DataBaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    private EditText edtUsuario , edtContrasena;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin =  findViewById(R.id.btnLogin);
        edtUsuario =  findViewById(R.id.edtUsuario);
        edtContrasena =  findViewById(R.id.edtContrasena);


        //Abrimos la base de datos DBUsuarios
        dbHelper  = new DataBaseOpenHelper(this , "DBCh11" , null , 2);
        db = dbHelper.getWritableDatabase();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuarioIngresado  = edtUsuario.getText().toString();
                String contrasenaIngresada  =  edtContrasena.getText().toString();

                if (usuarioIngresado.equals("")){
                    edtUsuario.setError("Valor requerido");
                    edtUsuario.requestFocus();
                    return;
                }

                else if (contrasenaIngresada.equals("")){
                    edtContrasena.setError("Valor requerido");
                    edtContrasena.requestFocus();
                    return;
                }


                else if(db!=null){
                    Cursor cursor = db.rawQuery("SELECT * FROM usuario INNER JOIN estado_usuario ON estado_usuario.idEstado = usuario.idEstado INNER JOIN rol_usuario ON rol_usuario.idRolUsuario =  usuario.idRolUsuario  " , null);


                    if(cursor==null){
                        Toast.makeText(getApplicationContext(), usuarioIngresado, Toast.LENGTH_SHORT).show();
                    }

                    if(cursor.moveToFirst()){

                        String nombre="";
                        String apellido="";
                        String usuario;
                        String contrasena;
                        String estado ="";
                        String rol = "";
                        String idRolUsuario = "";

                        boolean login  =  false;
                        while(cursor.isAfterLast() ==  false){

                            usuario = cursor.getString(cursor.getColumnIndex("usuario"));
                            contrasena =  cursor.getString(cursor.getColumnIndex("password"));



                            if(usuarioIngresado.equals(usuario) &&   contrasenaIngresada.equals(contrasena)  ){
                                nombre  = cursor.getString( cursor.getColumnIndex("nombreCompleto") );
                                apellido =  cursor.getString(cursor.getColumnIndex("correo"));
                                estado =  cursor.getString(cursor.getColumnIndex("estado"));
                                rol =  cursor.getString(cursor.getColumnIndex("rol"));
                                idRolUsuario = cursor.getString(cursor.getColumnIndex("idRolUsuario"));


                                login =  true;
                            }

                            cursor.moveToNext();
                        }

                        if(login){

                            Toast.makeText(getApplicationContext() , "Bienvenido "+nombre+" "+apellido+" "+estado+" "+rol ,  Toast.LENGTH_SHORT).show();

                            Intent i ;
                            if(idRolUsuario.equals("1")){
                                i  = new Intent(getApplicationContext() , HomeAdmin.class);
                            }else if(idRolUsuario.equals("2")){
                                i  = new Intent(getApplicationContext() ,HomeAsistente.class);
                            }else{
                                i  = new Intent(getApplicationContext() , HomeUsuario.class);
                            }

                            startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(), "Credenciales invalidas", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else{

                    Toast.makeText( getApplicationContext() , "Base de datos no inicializada " , Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}
