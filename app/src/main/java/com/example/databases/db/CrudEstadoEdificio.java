package com.example.databases.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.model.EstadoEdificio;
import com.example.databases.db.ContratoReservas.TablaEdificio;
import com.example.databases.db.ContratoReservas.TablaEstadoEdificio;

import java.util.ArrayList;

public class CrudEstadoEdificio {

    private SQLiteDatabase db;

    public CrudEstadoEdificio(Context context){
        db =  Conexion.obtenerBaseDatosLectura( context ,  null  );
    }

    public ArrayList<EstadoEdificio> listarEstadosEdificicios(){

        ArrayList<EstadoEdificio> estadosEdificios =  new ArrayList<>();

        StringBuilder queryEstadosEdificios = new StringBuilder();

        queryEstadosEdificios.append("SELECT *  FROM "+ TablaEstadoEdificio.tableName);

        Cursor cursor = db.rawQuery(queryEstadosEdificios.toString() , null);


        if(cursor.moveToFirst()){
            while(cursor.isAfterLast()==false){

                EstadoEdificio estadoEdificio =  new EstadoEdificio();
                estadoEdificio.setIdEstado(    Integer.parseInt( cursor.getString( cursor.getColumnIndex( TablaEstadoEdificio.idEstado    )   )    )    );
                estadoEdificio.setEstado(  cursor.getString( cursor.getColumnIndex(  TablaEstadoEdificio.estado   )    )   );
                estadosEdificios.add( estadoEdificio );
                cursor.moveToNext();
            }
        }

        return estadosEdificios;
    }

}
