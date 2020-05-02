package com.example.databases.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.databases.model.Edificio;
import com.example.databases.db.ContratoReservas.TablaEdificio;
import com.example.databases.db.ContratoReservas.TablaEstadoEdificio;
import com.example.databases.model.EstadoEdificio;

import java.util.ArrayList;


public class CrudEdificio {

    SQLiteDatabase db;

    public CrudEdificio(Context context){
        db =  Conexion.obtenerBaseDatosLectura(context ,  null);
    }

    public ArrayList<Edificio> listarEdificios(){

        ArrayList<Edificio>  edificios =  new ArrayList<>();

        StringBuilder queryEdificios =  new StringBuilder();

        queryEdificios.append("SELECT * FROM "+TablaEdificio.tableName);
        queryEdificios.append(" INNER JOIN "+TablaEstadoEdificio.tableName);
        queryEdificios.append(" ON "+TablaEdificio.tableName+"."+TablaEdificio.idEstado);
        queryEdificios.append(" = "+TablaEstadoEdificio.tableName+"."+TablaEstadoEdificio.idEstado);

        Cursor cursor   =  db.rawQuery(queryEdificios.toString(), null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast()==false){
                Edificio edificio =  new Edificio();
                EstadoEdificio estadoEdificio =  new EstadoEdificio();
                edificio.setIdEdificio(   Integer.parseInt( cursor.getString(  cursor.getColumnIndex( TablaEdificio.idEdificio    ))  ) );
                edificio.setDescripcion( cursor.getString(  cursor.getColumnIndex( TablaEdificio.direccion    )  )    );
                edificio.setFechaCreacion( cursor.getString( cursor.getColumnIndex( TablaEdificio.fechaCreacion   )  ));;
                edificio.setNombre(  cursor.getString(  cursor.getColumnIndex( TablaEdificio.nombre   )  )  );
                estadoEdificio.setIdEstado(  Integer.parseInt(  cursor.getString(   cursor.getColumnIndex(  TablaEstadoEdificio.idEstado  ) )   )  );
                edificio.setEstadoEdificio(estadoEdificio);
                edificios.add(edificio);
                cursor.moveToNext();
            }
        }
        return edificios;
    }

    public Edificio obtenerEdificio(int idEdificio){

        Edificio edificio =  new Edificio();

        StringBuilder queryEdificios =  new StringBuilder();

        queryEdificios.append("SELECT * FROM "+TablaEdificio.tableName);
        queryEdificios.append(" INNER JOIN "+TablaEstadoEdificio.tableName);
        queryEdificios.append(" ON "+TablaEdificio.tableName+"."+TablaEdificio.idEstado);
        queryEdificios.append(" = "+TablaEstadoEdificio.tableName+"."+TablaEstadoEdificio.idEstado);
        queryEdificios.append(" WHERE "+TablaEdificio.tableName+"."+TablaEdificio.idEdificio+"="+idEdificio);

        Cursor cursor   =  db.rawQuery(queryEdificios.toString(), null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast()==false){
                edificio =  new Edificio();
                EstadoEdificio estadoEdificio =  new EstadoEdificio();
                edificio.setIdEdificio(   Integer.parseInt( cursor.getString(  cursor.getColumnIndex( TablaEdificio.idEdificio    ))  ) );
                edificio.setDescripcion( cursor.getString(  cursor.getColumnIndex( TablaEdificio.descripcion    )  )    );
                edificio.setDireccion( cursor.getString(  cursor.getColumnIndex( TablaEdificio.direccion    )  )    );
                edificio.setFechaCreacion( cursor.getString( cursor.getColumnIndex( TablaEdificio.fechaCreacion   )  ));;
                edificio.setNombre(  cursor.getString(  cursor.getColumnIndex( TablaEdificio.nombre   )  )  );
                estadoEdificio.setIdEstado(  Integer.parseInt(  cursor.getString(   cursor.getColumnIndex(  TablaEstadoEdificio.idEstado  ) )   )  );
                edificio.setEstadoEdificio(estadoEdificio);
                cursor.moveToNext();
            }
        }
        return edificio;
    }

    public Edificio agregarEdificio(Edificio edificio){

        ContentValues contentValues =  new ContentValues();

        contentValues.put( TablaEdificio.nombre , edificio.getNombre()   );
        contentValues.put( TablaEdificio.descripcion ,  edificio.getDescripcion() );
        contentValues.put( TablaEdificio.direccion ,  edificio.getDireccion());
        contentValues.put( TablaEdificio.fechaCreacion , edificio.getFechaCreacion() );
        contentValues.put( TablaEdificio.idEstado ,  edificio.getEstadoEdificio().getIdEstado() );

        db.insert( TablaEdificio.tableName ,  null ,  contentValues  );

        return edificio;
    }

    public Edificio editarEdificio(Edificio edificio){

        ContentValues  contentValues =  new ContentValues();
        contentValues.put( TablaEdificio.nombre , edificio.getNombre()   );
        contentValues.put( TablaEdificio.descripcion ,  edificio.getDescripcion() );
        contentValues.put( TablaEdificio.direccion ,  edificio.getDireccion());
        contentValues.put( TablaEdificio.idEstado ,  edificio.getEstadoEdificio().getIdEstado() );
        db.update( TablaEdificio.tableName, contentValues , TablaEdificio.idEdificio+"="+edificio.getIdEdificio() , null  );

        return edificio;
    }


}
