package com.example.databases.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databases.R;
import com.example.databases.adapters.EstadoUsuarioAdapter;
import com.example.databases.adapters.RolUsuarioAdapter;
import com.example.databases.db.ContratoReservas;
import com.example.databases.db.CrudEstadoUsuario;
import com.example.databases.db.CrudRolUsuario;
import com.example.databases.db.CrudUsuarios;
import com.example.databases.model.EstadoUsuario;
import com.example.databases.model.RolUsuario;
import com.example.databases.model.Usuario;

import java.util.ArrayList;

public class FormularioUsuarios extends AppCompatActivity {
    private Spinner spinner, spinner2;
    EditText edtUsuario, edtNombre , edtCarnet , edtCorreo , edtTelefono , edtContrasena , edtFecha;
    Button btnCrear;
    Usuario usuario=new Usuario();
    Button btnSalir;
    EstadoUsuario estadoUsuario ;
    RolUsuario  rolUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuarios);

        final Intent i =  getIntent();
        btnSalir=findViewById(R.id.btnSalir);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtNombre = findViewById(R.id.edtNombre);
        edtCarnet = findViewById(R.id.edtCarnet);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtContrasena = findViewById(R.id.edtContrasena);
        edtFecha = findViewById(R.id.edtFecha);
        btnCrear =  findViewById(R.id.btnCrear);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);



        final CrudUsuarios crudUsuarios =  new CrudUsuarios(getApplicationContext());
        final CrudEstadoUsuario crudEstadoUsuario = new CrudEstadoUsuario(getApplicationContext());
        final CrudRolUsuario crudRolUsuario =  new CrudRolUsuario(getApplicationContext());

        final ArrayList<EstadoUsuario> estadoUsuarioArrayList =  crudEstadoUsuario.listarEstadosUsuarios();
        final ArrayList<RolUsuario> rolUsuarioArrayList =  crudRolUsuario.listarRolesUsuario();


        EstadoUsuarioAdapter estadoUsuarioAdapter = new EstadoUsuarioAdapter(getApplicationContext() , R.layout.custom_simple_spinner_item ,  estadoUsuarioArrayList   );
        final RolUsuarioAdapter rolUsuarioAdapter = new RolUsuarioAdapter(getApplicationContext(), R.layout.custom_simple_spinner_item , rolUsuarioArrayList );

        spinner.setAdapter(rolUsuarioAdapter);
        spinner2.setAdapter(estadoUsuarioAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rolUsuario = rolUsuarioArrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoUsuario = estadoUsuarioArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(i.hasExtra(ContratoReservas.TablaUsuario.idusuario)){
            String id = i.getStringExtra(ContratoReservas.TablaUsuario.idusuario);
            usuario = crudUsuarios.obtenerUsuario( Integer.parseInt(id));
            //Toast.makeText(getApplicationContext(),   usuario.getUsuario()  , Toast.LENGTH_SHORT).show();
            edtUsuario.setText(  usuario.getUsuario()   );
            edtNombre.setText( usuario.getNombreCompleto());
            edtCarnet.setText(usuario.getCarnet());
            edtCorreo.setText(  usuario.getCorreo()  );
            edtTelefono.setText(usuario.getTelefono());
            edtFecha.setText(usuario.getFechaCreacion());
            edtContrasena.setText( usuario.getPassword()  );
            btnCrear.setText("Actualizar");

            estadoUsuario = usuario.getEstadoUsuario();
            rolUsuario =  usuario.getRolUsuario();

            spinner.setSelection( (rolUsuario.getIdRolUsuario() - 1));
            spinner2.setSelection(  (estadoUsuario.getIdEstado()  -1)  );
        }

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edtUsuario.getText().toString();
                String nombreCompleto = edtNombre.getText().toString();
                String carnet =  edtCarnet.getText().toString();
                String correo =  edtCorreo.getText().toString();
                String telefono  =  edtTelefono.getText().toString();
                String contrasena  =  edtContrasena.getText().toString();

                 if(TextUtils.isEmpty(nombreCompleto)){
                    edtNombre.setError("Campo requerido");
                }else if(TextUtils.isEmpty(carnet)){
                    edtCarnet.setError("Campo requerido");
                }else if(TextUtils.isEmpty(correo)){
                    edtCorreo.setError("Campo requerido");
                }else if(TextUtils.isEmpty(telefono)){
                    edtTelefono.setError("Campo requerido");
                }else if(TextUtils.isEmpty(contrasena)){
                    edtContrasena.setError("Campo requerido");
                }else{

                    //usuario.setUsuario(user);
                    usuario.setNombreCompleto(nombreCompleto);
                    usuario.setCarnet(carnet);
                    usuario.setCorreo(correo);
                    usuario.setTelefono(telefono);
                    usuario.setPassword(contrasena);
                    usuario.setEstadoUsuario(estadoUsuario);
                    usuario.setRolUsuario(rolUsuario);
                    usuario.setUsuario(user);

                    if(i.hasExtra(ContratoReservas.TablaUsuario.idusuario)){
                        usuario.setIdUsuario(  Integer.parseInt(i.getStringExtra(ContratoReservas.TablaUsuario.idusuario))  );
                        crudUsuarios.editarUsuario(usuario);
                        Toast.makeText(FormularioUsuarios.this, "Usuario actualizado correctamente"  , Toast.LENGTH_SHORT).show();
                    }else{

                        crudUsuarios.agregarUsuario(usuario);
                        Toast.makeText(FormularioUsuarios.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext() ,ListaUsuarios.class  );
                startActivity(i);
                finish();
            }
        });
    }
}
