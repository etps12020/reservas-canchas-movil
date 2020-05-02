package com.example.databases.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

//Clase de manejo de la conexion (Solo recupera he inicializa la base de datos para no utilizar directamente el helper)
public class Conexion {

    private static final String DATABASE_NAME = "v0816"; //Nombre de la base de datos
    private static final int VERSION_ACTUAL = 1; //Version de la base de datos
    private static DbOpenHelper baseDatos; //H

    //Metodo para obtener la b
    public static SQLiteDatabase obtenerBaseDatosEscritura(Context context , SQLiteDatabase.CursorFactory factory){

        //Si la coneexion yay esta inicializada solo devuelve la instancia
        if(baseDatos==null){
            baseDatos = new DbOpenHelper(context , DATABASE_NAME  , factory , VERSION_ACTUAL);
        }
        return  baseDatos.getWritableDatabase();
    }

    //Metodo para obte
    public static SQLiteDatabase obtenerBaseDatosLectura(Context context  , SQLiteDatabase.CursorFactory factory ){

        //Si la coneexion yay esta inicializada solo devuelve la instancia
        if(baseDatos==null){
            baseDatos = new DbOpenHelper(context , DATABASE_NAME  , factory , VERSION_ACTUAL );
        }
        return  baseDatos.getReadableDatabase();
    }





}
