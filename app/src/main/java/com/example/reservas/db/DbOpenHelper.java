package com.example.reservas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Crea la base de datos , sus registros
public class DbOpenHelper extends SQLiteOpenHelper {


    private DdlDb ddlDb =  new DdlDb(); //Para creacion de tablas y eliminacion de las mismas
    private DmlDb dmlDb =  new DmlDb(); //para ingreso de datos por defecto a la base de datos


    public DbOpenHelper(Context context  , String name  , SQLiteDatabase.CursorFactory factory , int version){
        super(  context , name , factory ,  version  );
    }

    //Crea las tablas y relaciones de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tablas catalogo (Tablas sin llaves foraneas)
        db.execSQL(  ddlDb.createTableRolUsuario()  );
        db.execSQL(  ddlDb.createTableEstadoUsuario()  );
        db.execSQL(  ddlDb.createTableEstadoCancha()  );
        db.execSQL(  ddlDb.createTableEstadoEdificio()  );
        db.execSQL(  ddlDb.createTableEstadoHistorico()  );
        db.execSQL(  ddlDb.createTableEstadoImagen() );
        db.execSQL(  ddlDb.createTableEstadoReservacion() );
        db.execSQL(  ddlDb.createTableTipoReservacion());
        db.execSQL(  ddlDb.createTableTipoCancha());


        //Tablas entidad (Tablas con llaves foraneas)
        db.execSQL( ddlDb.createTableEdificio());
        db.execSQL( ddlDb.createTableCancha());
        db.execSQL( ddlDb.createTableRestricciones());
        db.execSQL( ddlDb.createTableImagen());
        db.execSQL( ddlDb.createTableUsuarios());
        db.execSQL( ddlDb.createTableReservacion());
        db.execSQL( ddlDb.createTableHistorico());


        //Creacion de registros por defecto
        dmlDb.crearRolesUsuariosDefault(db);
        dmlDb.crearEstadoUsuarioDeafult(db);
        dmlDb.crearUsuariosDefault(db);
    }

    //Elimina la base de datos y crea nuevamente ante un cambio de version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( ddlDb.dropTableImagen());
        db.execSQL( ddlDb.dropTableEdificio());
        db.execSQL( ddlDb.dropTableCancha());
        db.execSQL( ddlDb.dropTableHistorico());
        db.execSQL( ddlDb.dropTableReservacion());
        db.execSQL( ddlDb.dropTableUsuarios());

        //Tablas catalogo
        db.execSQL(  ddlDb.dropTableRolUsuario()  );
        db.execSQL(  ddlDb.dropTableEstadoUsuario()  );
        db.execSQL(  ddlDb.dropTableEstadoCancha()  );
        db.execSQL(  ddlDb.dropTableEstadoEdificio()  );
        db.execSQL(  ddlDb.dropTableEstadoHistorico()  );
        db.execSQL(  ddlDb.dropTableEstadoImagen() );
        db.execSQL(  ddlDb.dropTableEstadoReservacion() );
        db.execSQL(  ddlDb.dropTableTipoReservacion());
        db.execSQL(  ddlDb.dropTableTipoCancha());

        this.onCreate(db);
    }
}
