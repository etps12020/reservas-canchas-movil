package com.example.databases.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.databases.db.Conexion;

import com.example.databases.model.EstadoUsuario;
import com.example.databases.model.RolUsuario;
import com.example.databases.model.Usuario;
import com.example.databases.db.ContratoReservas.TablaUsuario;
import com.example.databases.db.ContratoReservas.TablaRolUsuario;
import com.example.databases.db.ContratoReservas.TablaEstadoUsuario;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

//Contiene los metodos para crud de usuairos (Listar  , obtener por id  , insertar  , actualizar)
public class CrudUsuarios {

    SQLiteDatabase db;

    public CrudUsuarios(Context context ){
        //Inicializa la conexion
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }


    //Listado de usaurios
    public ArrayList<Usuario> listarUsuarios(){
        ArrayList<Usuario> usuarios= new ArrayList<>();

        StringBuilder queryUsuarios  =  new StringBuilder();

        queryUsuarios.append("SELECT *  FROM "+TablaUsuario.tableName+" ");
        queryUsuarios.append("INNER JOIN "+TablaRolUsuario.tableName+" ON ");
        queryUsuarios.append(TablaUsuario.tableName+"."+TablaUsuario.idRol +"="+TablaRolUsuario.tableName+"."+TablaRolUsuario.idRolUsuario);
        queryUsuarios.append(" INNER JOIN "+TablaEstadoUsuario.tableName+" ON ");
        queryUsuarios.append(TablaUsuario.tableName+"."+TablaUsuario.idEstado +"="+TablaEstadoUsuario.tableName+"."+TablaEstadoUsuario.idEstado);


        Cursor cursor   =  db.rawQuery(queryUsuarios.toString(), null);

                if(cursor.moveToFirst()){
                    while(cursor.isAfterLast() == false){

                Usuario usuario =  new Usuario();
                EstadoUsuario estadoUsuario =  new EstadoUsuario();
                RolUsuario rolUsuario  = new RolUsuario();

                //Rol del usuario
                rolUsuario.setIdRolUsuario(   Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaUsuario.idRol))   ) );
                rolUsuario.setRol( cursor.getString( cursor.getColumnIndex(TablaRolUsuario.rol)   ));

                //Estado del usuario del usuario
                estadoUsuario.setIdEstado( Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaEstadoUsuario.idEstado))));
                estadoUsuario.setEstado(cursor.getString( cursor.getColumnIndex(TablaEstadoUsuario.estado)));

                //Usuario
                usuario.setIdUsuario(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaUsuario.idusuario))     )  );
                usuario.setNombreCompleto(cursor.getString( cursor.getColumnIndex(TablaUsuario.nombreCompleto) ));
                usuario.setUsuario(cursor.getString( cursor.getColumnIndex(TablaUsuario.usuario) ));
                usuario.setCarnet(cursor.getString( cursor.getColumnIndex(TablaUsuario.carnet) ));
                usuario.setCorreo(cursor.getString( cursor.getColumnIndex(TablaUsuario.correo) ));
                usuario.setPassword(cursor.getString( cursor.getColumnIndex(TablaUsuario.password) ));
                usuario.setFechaCreacion (cursor.getString( cursor.getColumnIndex(TablaUsuario.fechaCreacion) ));
                usuario.setEstadoUsuario( estadoUsuario );
                usuario.setRolUsuario( rolUsuario );

                usuarios.add(usuario);

                cursor.moveToNext();
            }
        }

        return  usuarios;
    }

    //Obtener usuario especifico
    public  Usuario obtenerUsuario(int idUsuario){

        Usuario usuario  =  new Usuario();

        StringBuilder queryUsuario  =  new StringBuilder();

        queryUsuario.append("SELECT *  FROM "+TablaUsuario.tableName+" ");
        queryUsuario.append(" INNER JOIN "+TablaRolUsuario.tableName+" ON ");
        queryUsuario.append(TablaUsuario.tableName+"."+TablaUsuario.idRol +"="+TablaRolUsuario.tableName+"."+TablaRolUsuario.idRolUsuario);
        queryUsuario.append(" INNER JOIN "+TablaEstadoUsuario.tableName+" ON ");
        queryUsuario.append(TablaUsuario.tableName+"."+TablaUsuario.idEstado +"="+TablaEstadoUsuario.tableName+"."+TablaEstadoUsuario.idEstado);
        queryUsuario.append(" WHERE "+TablaUsuario.tableName+"."+TablaUsuario.idusuario+" = "+ idUsuario);


        Cursor cursor =  db.rawQuery( queryUsuario.toString() , null );

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){

                EstadoUsuario estadoUsuario =  new EstadoUsuario();
                RolUsuario rolUsuario  = new RolUsuario();

                //Rol del usuario
                rolUsuario.setIdRolUsuario(   Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaUsuario.idRol))   ) );
                rolUsuario.setRol( cursor.getString( cursor.getColumnIndex(TablaRolUsuario.rol)   ));

                //Estado del usuario del usuario
                estadoUsuario.setIdEstado( Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaEstadoUsuario.idEstado))));
                estadoUsuario.setEstado(cursor.getString( cursor.getColumnIndex(TablaEstadoUsuario.estado)));

                //Usuario
                usuario.setIdUsuario( Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaUsuario.idusuario  ) ))     );
                usuario.setNombreCompleto(cursor.getString( cursor.getColumnIndex(TablaUsuario.nombreCompleto) ));
                usuario.setUsuario(cursor.getString( cursor.getColumnIndex(TablaUsuario.usuario) ));
                usuario.setCarnet(cursor.getString( cursor.getColumnIndex(TablaUsuario.carnet) ));
                usuario.setCorreo(cursor.getString( cursor.getColumnIndex(TablaUsuario.correo) ));
                usuario.setPassword(cursor.getString( cursor.getColumnIndex(TablaUsuario.password) ));
                usuario.setTelefono( cursor.getString( cursor.getColumnIndex(TablaUsuario.telefono )));
                usuario.setFechaCreacion (cursor.getString( cursor.getColumnIndex(TablaUsuario.fechaCreacion) ));
                usuario.setEstadoUsuario( estadoUsuario );
                usuario.setRolUsuario( rolUsuario );

                cursor.moveToNext();
            }
        }

        return usuario;
    }

    public Usuario editarUsuario(Usuario usuario){

        ContentValues contentValues = new ContentValues();

        contentValues.put( TablaUsuario.nombreCompleto  , usuario.getNombreCompleto()  );
        contentValues.put(TablaUsuario.correo ,  usuario.getCorreo()   );
        contentValues.put( TablaUsuario.carnet , usuario.getCarnet());
        contentValues.put(TablaUsuario.password ,  usuario.getPassword());
        contentValues.put(TablaUsuario.telefono ,  usuario.getTelefono());
        contentValues.put( TablaUsuario.idRol , usuario.getRolUsuario().getIdRolUsuario()  );

        contentValues.put( TablaUsuario.idEstado , usuario.getEstadoUsuario().getIdEstado());

        db.update(  TablaUsuario.tableName , contentValues ,  TablaUsuario.idusuario+"="+usuario.getIdUsuario() ,null  );

        return usuario;
    }

    public Usuario agregarUsuario(Usuario usuario){

        ContentValues contentValues = new ContentValues();

        contentValues.put(TablaUsuario.carnet , usuario.getCarnet());
        contentValues.put(TablaUsuario.correo ,  usuario.getCorreo());
        contentValues.put(TablaUsuario.usuario , usuario.getUsuario()   );
        contentValues.put(TablaUsuario.password , usuario.getPassword());
        contentValues.put(TablaUsuario.telefono , usuario.getTelefono());
        contentValues.put(TablaUsuario.nombreCompleto ,  usuario.getNombreCompleto() );
        contentValues.put(TablaUsuario.idEstado , usuario.getEstadoUsuario().getIdEstado());
        contentValues.put(TablaUsuario.idRol , usuario.getRolUsuario().getIdRolUsuario());
        contentValues.put(TablaUsuario.fechaCreacion ,  usuario.getFechaCreacion());


        db.insert(TablaUsuario.tableName ,  null  , contentValues);

        return usuario;
    }

    public void inhabilitarUsuario(Usuario usuario){

    }

    public Usuario login(Usuario usuario){
        Usuario usuarioLogin=null;
        String sqlLogin  = "SELECT *  FROM  "+TablaUsuario.tableName+" WHERE "+TablaUsuario.usuario+"= '"+usuario.getUsuario()+"' AND "+TablaUsuario.password+" = '"+usuario.getPassword()+"'";
        Cursor cursor = db.rawQuery(sqlLogin , null);
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){
                usuarioLogin = new Usuario();
                EstadoUsuario estadoUsuario =  new EstadoUsuario();
                RolUsuario rolUsuario =  new RolUsuario();

                estadoUsuario.setIdEstado( Integer.parseInt(cursor.getString(cursor.getColumnIndex("idEstado"))  ));
                rolUsuario.setIdRolUsuario( Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRol"))  ));

                usuarioLogin.setCarnet(cursor.getString( cursor.getColumnIndex(TablaUsuario.carnet)));
                usuarioLogin.setCorreo(cursor.getString( cursor.getColumnIndex(TablaUsuario.correo)));
                usuarioLogin.setNombreCompleto(cursor.getString( cursor.getColumnIndex(TablaUsuario.nombreCompleto)   ));
                usuarioLogin.setFechaCreacion(cursor.getString(cursor.getColumnIndex(TablaUsuario.fechaCreacion)));
                usuarioLogin.setEstadoUsuario(estadoUsuario);
                usuarioLogin.setRolUsuario(rolUsuario);
                cursor.moveToNext();
            }

        }

        return usuarioLogin;
    }


}
