package com.example.databases.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.db.Conexion;
import com.example.databases.model.EstadoUsuario;

import java.util.ArrayList;
import com.example.databases.db.ContratoReservas.TablaEstadoUsuario;

public class CrudEstadoUsuario {

    SQLiteDatabase db;

    //Constructor inicializa la base de datos para ser utilizada en esta clase
    public CrudEstadoUsuario(Context context){
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }

    //Lista los  usuarios
    public ArrayList<EstadoUsuario> listarEstadosUsuarios(){
        ArrayList<EstadoUsuario> estadoUsuarioArrayList =  new ArrayList<>();
        String sqlListarEstados = "SELECT *  FROM "+TablaEstadoUsuario.tableName;
        Cursor cursor =  db.rawQuery(sqlListarEstados, null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                EstadoUsuario estadoUsuario =  new EstadoUsuario();
                estadoUsuario.setIdEstado(  Integer.parseInt( cursor.getString(cursor.getColumnIndex(TablaEstadoUsuario.idEstado))  ) );
                estadoUsuario.setEstado(   cursor.getString(cursor.getColumnIndex(TablaEstadoUsuario.estado))   );
                estadoUsuarioArrayList.add(estadoUsuario);
                cursor.moveToNext();
            }
        }

        return  estadoUsuarioArrayList;
    }
}
