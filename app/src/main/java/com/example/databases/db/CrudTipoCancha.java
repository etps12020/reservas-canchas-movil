package com.example.databases.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.model.RolUsuario;
import com.example.databases.model.TipoCancha;

import java.util.ArrayList;

public class CrudTipoCancha
{
    SQLiteDatabase db;

    //Constructor inicializa la base de datos para ser utilizada en esta clase
    public CrudTipoCancha(Context context){
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }

    //Lista los roles disponibles en la base de datos
    public ArrayList<TipoCancha> listarTiposCancha(){
        ArrayList<TipoCancha> tipoCanchaArrayList =  new ArrayList<>();
        String sqlListarEstados = "SELECT *  FROM "+ ContratoReservas.TablaTipoCancha.tableName;
        Cursor cursor =  db.rawQuery(sqlListarEstados, null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                TipoCancha tipoCancha =  new TipoCancha();
                tipoCancha.setIdTipoCancha(  Integer.parseInt( cursor.getString(cursor.getColumnIndex(ContratoReservas.TablaTipoCancha.idTipoCancha))  ) );
                tipoCancha.setTipo(   cursor.getString(cursor.getColumnIndex(ContratoReservas.TablaTipoCancha.tipo))   );
                tipoCanchaArrayList.add(tipoCancha);
                cursor.moveToNext();
            }
        }

        return  tipoCanchaArrayList;
    }

}
