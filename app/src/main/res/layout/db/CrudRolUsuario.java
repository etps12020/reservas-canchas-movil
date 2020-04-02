package com.example.databases.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.db.Conexion;
import com.example.databases.db.ContratoReservas.TablaRolUsuario;
import com.example.databases.model.RolUsuario;

import java.util.ArrayList;

public class CrudRolUsuario {

    SQLiteDatabase db;

    //Constructor inicializa la base de datos para ser utilizada en esta clase
    public CrudRolUsuario(Context context){
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }

    //Lista los roles disponibles en la base de datos
    public ArrayList<RolUsuario> listarRolesUsuario(){
        ArrayList<RolUsuario> rolUsuarioArrayList =  new ArrayList<>();
        String sqlListarEstados = "SELECT *  FROM "+TablaRolUsuario.tableName;
        Cursor cursor =  db.rawQuery(sqlListarEstados, null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                RolUsuario rolUsuario =  new RolUsuario();
                rolUsuario.setIdRolUsuario(  Integer.parseInt( cursor.getString(cursor.getColumnIndex(TablaRolUsuario.idRolUsuario))  ) );
                rolUsuario.setRol(   cursor.getString(cursor.getColumnIndex(TablaRolUsuario.rol))   );
                rolUsuarioArrayList.add(rolUsuario);
                cursor.moveToNext();
            }
        }

        return  rolUsuarioArrayList;
    }


}
