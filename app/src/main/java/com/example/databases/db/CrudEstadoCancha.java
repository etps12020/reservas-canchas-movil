package com.example.databases.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.model.EstadoCancha;

import java.util.ArrayList;

public class CrudEstadoCancha
{
    SQLiteDatabase db;

    //Constructor inicializa la base de datos para ser utilizada en esta clase
    public CrudEstadoCancha(Context context){
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }

    //Lista los  usuarios
    public ArrayList<EstadoCancha> listarEstadosCanchas(){
        ArrayList<EstadoCancha> estadoCanchaArrayList =  new ArrayList<>();
        String sqlListarEstados = "SELECT *  FROM "+ ContratoReservas.TablaEstadoCancha.tableName;
        Cursor cursor =  db.rawQuery(sqlListarEstados, null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                EstadoCancha estadoCancha =  new EstadoCancha();
                estadoCancha.setIdEstado(  Integer.parseInt( cursor.getString(cursor.getColumnIndex(ContratoReservas.TablaEstadoCancha.idEstado))  ) );
                estadoCancha.setEstado(   cursor.getString(cursor.getColumnIndex(ContratoReservas.TablaEstadoCancha.estado))   );
                estadoCanchaArrayList.add(estadoCancha);
                cursor.moveToNext();
            }
        }

        return  estadoCanchaArrayList;
    }


}
