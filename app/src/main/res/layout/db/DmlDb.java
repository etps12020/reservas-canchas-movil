
package com.example.databases.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.db.ContratoReservas.TablaEstadoUsuario;
import com.example.databases.db.ContratoReservas.TablaRolUsuario;
import com.example.databases.db.ContratoReservas.TablaUsuario;


//Clase para creacion de registros por defecto en la base de datos, sentencias de manipulacion de datos (Data Manipulation Language)
public class DmlDb {


    public void crearUsuariosDefault(SQLiteDatabase db){

        ContentValues administrador  =  new ContentValues();
        administrador.put(TablaUsuario.usuario ,  "administrador");
        administrador.put(TablaUsuario.nombreCompleto , "ListaUsuarios");
        administrador.put(TablaUsuario.password ,  "utec4ever");
        administrador.put(TablaUsuario.idEstado ,  1);
        administrador.put(TablaUsuario.idRol ,  1);
        db.insert( TablaUsuario.tableName ,  null   , administrador);

        ContentValues asistente  =  new ContentValues();
        asistente.put(TablaUsuario.usuario ,  "asistente");
        asistente.put(TablaUsuario.nombreCompleto , "Asistente");
        asistente.put(TablaUsuario.password ,  "utec4ever");
        asistente.put(TablaUsuario.idEstado ,  1);
        asistente.put(TablaUsuario.idRol ,  2);
        db.insert( TablaUsuario.tableName ,  null   , asistente);


        ContentValues usuario  =  new ContentValues();
        usuario.put(TablaUsuario.usuario ,  "usuario");
        usuario.put(TablaUsuario.nombreCompleto , "usuario");
        usuario.put(TablaUsuario.password ,  "utec4ever");
        usuario.put(TablaUsuario.idEstado ,  1);
        usuario.put(TablaUsuario.idRol ,  2);
        db.insert( TablaUsuario.tableName ,  null   , usuario);

    }


    public void crearEstadoUsuarioDeafult(SQLiteDatabase db){
        ContentValues activo =  new ContentValues();
        activo.put(TablaEstadoUsuario.estado , "Activo");
        db.insert(TablaEstadoUsuario.tableName , null , activo);

        ContentValues inactivo =  new ContentValues();
        inactivo.put(TablaEstadoUsuario.estado , "Inactivo");
        db.insert(TablaEstadoUsuario.tableName , null , inactivo);

    }

    public void crearRolesUsuariosDefault(SQLiteDatabase db){

        ContentValues rolAdministrador =  new ContentValues();
        rolAdministrador.put(TablaRolUsuario.rol , "ListaUsuarios");
        db.insert( TablaRolUsuario.tableName ,  null   , rolAdministrador);

        ContentValues rolAsistente =  new ContentValues();
        rolAsistente.put(TablaRolUsuario.rol , "Asistente");
        db.insert( TablaRolUsuario.tableName ,  null   , rolAsistente);

        ContentValues rolUsuario =  new ContentValues();
        rolUsuario.put(TablaRolUsuario.rol , "Usuario");
        db.insert( TablaRolUsuario.tableName ,  null   , rolUsuario);
    }


}
