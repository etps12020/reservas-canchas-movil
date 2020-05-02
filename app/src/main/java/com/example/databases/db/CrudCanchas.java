package com.example.databases.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.IntegerRes;

import com.example.databases.model.EstadoCancha;
import com.example.databases.model.Edificio;
import com.example.databases.model.Cancha;
import com.example.databases.model.EstadoEdificio;
import com.example.databases.model.TipoCancha;
import com.example.databases.db.ContratoReservas.TablaEdificio;
import com.example.databases.db.ContratoReservas.TablaEstadoCancha;

import com.example.databases.db.ContratoReservas.TablaTipoCancha;
import com.example.databases.db.ContratoReservas.TablaCancha;

import java.util.ArrayList;

public class CrudCanchas
{
    SQLiteDatabase db;

    public CrudCanchas(Context context ){
        //Inicializa la conexion
        db = Conexion.obtenerBaseDatosLectura(context ,  null );
    }


    //Listado de usaurios
    public ArrayList<Cancha> listarCanchas(){
        ArrayList<Cancha> canchas= new ArrayList<>();

        StringBuilder queryCanchas  =  new StringBuilder();

        queryCanchas.append("SELECT *  FROM "+ TablaCancha.tableName+" ");
        queryCanchas.append("INNER JOIN "+ TablaEstadoCancha.tableName+" ON ");
        queryCanchas.append(TablaCancha.tableName+"."+ TablaCancha.idEstado +"="+ TablaEstadoCancha.tableName+"."+ TablaEstadoCancha.idEstado);
        queryCanchas.append(" INNER JOIN "+ TablaTipoCancha.tableName+" ON ");
        queryCanchas.append(TablaCancha.tableName+"."+ TablaCancha.idTipoCancha+"="+ TablaTipoCancha.tableName+"."+ TablaTipoCancha.idTipoCancha);
//        queryCanchas.append(" INNER JOIN "+ TablaEdificio.tableName+" ON ");
//        queryCanchas.append(TablaCancha.tableName+"."+ TablaCancha.idEdificio+"="+ TablaEdificio.tableName+"."+ TablaEdificio.idEdificio);


        Cursor cursor   =  db.rawQuery(queryCanchas.toString(), null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){

                Cancha cancha =  new Cancha();
                EstadoCancha estadoCancha =  new EstadoCancha();
                TipoCancha tipoCancha  = new TipoCancha();
//                Edificio edificio  = new Edificio();
//                EstadoEdificio estadoEdificio = new EstadoEdificio();
                //Rol del usuario
                estadoCancha.setIdEstado(   Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaEstadoCancha.idEstado))   ) );
                estadoCancha.setEstado( cursor.getString( cursor.getColumnIndex(TablaEstadoCancha.estado)   ));

                //Estado del usuario del usuario
                tipoCancha.setIdTipoCancha( Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaTipoCancha.idTipoCancha))));
                tipoCancha.setTipo(cursor.getString( cursor.getColumnIndex(TablaTipoCancha.tipo)));

                //Usuario
                cancha.setIdCancha(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaCancha.idCancha))     )  );
                cancha.setNombre(cursor.getString( cursor.getColumnIndex(TablaCancha.nombre) ));
                cancha.setDescripcion(cursor.getString( cursor.getColumnIndex(TablaCancha.descripcion) ));
                cancha.setTelefonoContacto(cursor.getString( cursor.getColumnIndex(TablaCancha.telefonoContacto) ));
                cancha.setHoraInicio(cursor.getString( cursor.getColumnIndex(TablaCancha.horaInicio) ));
                cancha.setHoraFin(cursor.getString( cursor.getColumnIndex(TablaCancha.horaFin) ));
                cancha.setFechaCreacion (cursor.getString( cursor.getColumnIndex(TablaCancha.fechaCreacion) ));

                cancha.setEstadoCancha( estadoCancha );
                cancha.setTipoCancha( tipoCancha );
//                cancha.setEdificio( edificio);
                canchas.add(cancha);

//
//                //Usuario
//                edificio.setIdEdificio(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaEdificio.idEdificio))     )  );
//                edificio.setNombre(cursor.getString( cursor.getColumnIndex(TablaEdificio.nombre) ));
//                edificio.setDireccion(cursor.getString( cursor.getColumnIndex(TablaEdificio.direccion) ));
//                edificio.setDescripcion(cursor.getString( cursor.getColumnIndex(TablaEdificio.descripcion) ));
//                edificio.setFechaCreacion(cursor.getString( cursor.getColumnIndex(TablaEdificio.fechaCreacion) ));

                cursor.moveToNext();
            }
        }

        return  canchas;
    }

    //Obtener usuario especifico
    public  Cancha obtenerCancha(int idCancha){

        Cancha cancha  =  new Cancha();

        StringBuilder queryCancha  =  new StringBuilder();

        queryCancha.append("SELECT *  FROM "+ TablaCancha.tableName+" ");
        queryCancha.append(" INNER JOIN "+ TablaEstadoCancha.tableName+" ON ");
        queryCancha.append(TablaCancha.tableName+"."+ TablaCancha.idEstado +"="+ TablaEstadoCancha.tableName+"."+ TablaEstadoCancha.idEstado);
        queryCancha.append(" INNER JOIN "+ ContratoReservas.TablaTipoCancha.tableName+" ON ");
        queryCancha.append(TablaCancha.tableName+"."+ TablaCancha.idTipoCancha +"="+ TablaTipoCancha.tableName+"."+ TablaTipoCancha.idTipoCancha);
        queryCancha.append(" WHERE "+ TablaCancha.tableName+"."+ TablaCancha.idCancha+" = "+ idCancha);


        Cursor cursor =  db.rawQuery( queryCancha.toString() , null );

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){

                EstadoCancha estadoCancha =  new EstadoCancha();
                TipoCancha tipoCancha  = new TipoCancha();
                Edificio edificio  = new Edificio();
                //Rol del usuario
                estadoCancha.setIdEstado(   Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaEstadoCancha.idEstado))   ) );
                estadoCancha.setEstado( cursor.getString( cursor.getColumnIndex(TablaEstadoCancha.estado)   ));

                //Estado del usuario del usuario
                tipoCancha.setIdTipoCancha( Integer.parseInt(cursor.getString( cursor.getColumnIndex(TablaTipoCancha.idTipoCancha))));
                tipoCancha.setTipo(cursor.getString( cursor.getColumnIndex(TablaTipoCancha.tipo)));

                //Usuario
                cancha.setIdCancha(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaCancha.idCancha))     )  );
                cancha.setNombre(cursor.getString( cursor.getColumnIndex(TablaCancha.nombre) ));
                cancha.setDescripcion(cursor.getString( cursor.getColumnIndex(TablaCancha.descripcion) ));
                cancha.setTelefonoContacto(cursor.getString( cursor.getColumnIndex(TablaCancha.telefonoContacto) ));
                cancha.setHoraInicio(cursor.getString( cursor.getColumnIndex(TablaCancha.horaInicio) ));
                cancha.setHoraFin(cursor.getString( cursor.getColumnIndex(TablaCancha.horaFin) ));
                cancha.setFechaCreacion (cursor.getString( cursor.getColumnIndex(TablaCancha.fechaCreacion) ));

                edificio.setIdEdificio(Integer.parseInt(cursor.getString( cursor.getColumnIndex( TablaCancha.idEdificio)  )));


                cancha.setEstadoCancha( estadoCancha );
                cancha.setTipoCancha( tipoCancha );
                cancha.setEdificio( edificio);



                //Usuario
//                edificio.setIdEdificio(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaEdificio.idEdificio))     )  );
//                edificio.setNombre(cursor.getString( cursor.getColumnIndex(TablaEdificio.nombre) ));
//                edificio.setDireccion(cursor.getString( cursor.getColumnIndex(TablaEdificio.direccion) ));
//                edificio.setDescripcion(cursor.getString( cursor.getColumnIndex(TablaEdificio.descripcion) ));
//                edificio.setFechaCreacion(cursor.getString( cursor.getColumnIndex(TablaEdificio.fechaCreacion) ));
                //edificio.setIdEstado(  Integer.parseInt(cursor.getString(cursor.getColumnIndex(TablaEdificio.idEstado))     )  );


                cursor.moveToNext();
            }
        }

        return cancha;
    }

    public Cancha editarCacha(Cancha cancha){

        ContentValues contentValues = new ContentValues();

        contentValues.put( TablaCancha.nombre  , cancha.getNombre()  );
        contentValues.put(TablaCancha.descripcion ,  cancha.getDescripcion()   );
        contentValues.put( TablaCancha.telefonoContacto , cancha.getTelefonoContacto());
        contentValues.put(TablaCancha.horaInicio ,  cancha.getHoraInicio());
        contentValues.put(TablaCancha.horaFin ,  cancha.getHoraFin());
        contentValues.put( TablaCancha.idEdificio , cancha.getEdificio().getIdEdificio() );

        contentValues.put( TablaCancha.idEstado , cancha.getEstadoCancha().getIdEstado());
        contentValues.put( TablaCancha.idTipoCancha , cancha.getTipoCancha().getIdTipoCancha());
        db.update( TablaCancha.tableName , contentValues ,  TablaCancha.idCancha+"="+cancha.getIdCancha() ,null  );

        return cancha;
    }

    public Cancha agregarCancha(Cancha cancha){

        ContentValues contentValues = new ContentValues();

        contentValues.put(TablaCancha.nombre , cancha.getNombre());
        contentValues.put(TablaCancha.descripcion ,  cancha.getDescripcion());
        contentValues.put(TablaCancha.telefonoContacto , cancha.getTelefonoContacto()   );
        contentValues.put(TablaCancha.horaInicio , cancha.getHoraInicio());
        contentValues.put(TablaCancha.horaFin, cancha.getHoraFin());

        contentValues.put(TablaCancha.idEstado , cancha.getEstadoCancha().getIdEstado());
        contentValues.put(TablaCancha.idEdificio , cancha.getEdificio().getIdEdificio());
        contentValues.put(TablaCancha.idTipoCancha , cancha.getTipoCancha().getIdTipoCancha());
        contentValues.put(TablaCancha.fechaCreacion ,  cancha.getFechaCreacion());


        db.insert(TablaCancha.tableName ,  null  , contentValues);

        return cancha;
    }

    public void inhabilitarCancha(Cancha cancha){

    }

    //public Usuario login(Usuario usuario){
      //  Usuario usuarioLogin=null;
     //   String sqlLogin  = "SELECT *  FROM  "+ ContratoReservas.TablaUsuario.tableName+" WHERE "+ ContratoReservas.TablaUsuario.usuario+"= '"+usuario.getUsuario()+"' AND "+ ContratoReservas.TablaUsuario.password+" = '"+usuario.getPassword()+"'";
   //     Cursor cursor = db.rawQuery(sqlLogin , null);
     //   if(cursor.moveToFirst()){
     //       while(cursor.isAfterLast() == false){
     //           usuarioLogin = new Usuario();
      //          EstadoUsuario estadoUsuario =  new EstadoUsuario();
      //          RolUsuario rolUsuario =  new RolUsuario();

      //          estadoUsuario.setIdEstado( Integer.parseInt(cursor.getString(cursor.getColumnIndex("idEstado"))  ));
      //          rolUsuario.setIdRolUsuario( Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRol"))  ));

      //          usuarioLogin.setCarnet(cursor.getString( cursor.getColumnIndex(ContratoReservas.TablaUsuario.carnet)));
         //       usuarioLogin.setCorreo(cursor.getString( cursor.getColumnIndex(ContratoReservas.TablaUsuario.correo)));
       //         usuarioLogin.setNombreCompleto(cursor.getString( cursor.getColumnIndex(ContratoReservas.TablaUsuario.nombreCompleto)   ));
       //         usuarioLogin.setFechaCreacion(cursor.getString(cursor.getColumnIndex(ContratoReservas.TablaUsuario.fechaCreacion)));
       //         usuarioLogin.setEstadoUsuario(estadoUsuario);
           //     usuarioLogin.setRolUsuario(rolUsuario);
        //        cursor.moveToNext();
           // }

      //  }

     //   return usuarioLogin;
  //  }



}
