
package com.example.databases.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.databases.db.ContratoReservas.TablaUsuario;
import com.example.databases.db.ContratoReservas.TablaEstadoUsuario;
import com.example.databases.db.ContratoReservas.TablaRolUsuario;
import com.example.databases.db.ContratoReservas.TablaCancha;
import com.example.databases.db.ContratoReservas.TablaEdificio;
import com.example.databases.db.ContratoReservas.TablaEstadoCancha;
import com.example.databases.db.ContratoReservas.TablaTipoCancha;
import com.example.databases.db.ContratoReservas.TablaEstadoEdificio;


//Clase para creacion de registros por defecto en la base de datos, sentencias de manipulacion de datos (Data Manipulation Language)
public class DmlDb {


    public void crearUsuariosDefault(SQLiteDatabase db) {

        ContentValues administrador = new ContentValues();
        administrador.put(TablaUsuario.usuario, "administrador");
        administrador.put(TablaUsuario.nombreCompleto, "Administrador");
        administrador.put(TablaUsuario.password, "utec4ever");
        administrador.put(TablaUsuario.idEstado, 1);
        administrador.put(TablaUsuario.idRol, 1);
        db.insert(TablaUsuario.tableName, null, administrador);

        ContentValues asistente = new ContentValues();
        asistente.put(TablaUsuario.usuario, "asistente");
        asistente.put(TablaUsuario.nombreCompleto, "Asistente");
        asistente.put(TablaUsuario.password, "utec4ever");
        asistente.put(TablaUsuario.idEstado, 1);
        asistente.put(TablaUsuario.idRol, 2);
        db.insert(TablaUsuario.tableName, null, asistente);


        ContentValues usuario = new ContentValues();
        usuario.put(TablaUsuario.usuario, "usuario");
        usuario.put(TablaUsuario.nombreCompleto, "usuario");
        usuario.put(TablaUsuario.password, "utec4ever");
        usuario.put(TablaUsuario.idEstado, 1);
        usuario.put(TablaUsuario.idRol, 2);
        db.insert(TablaUsuario.tableName, null, usuario);

    }


    public void crearEstadoUsuarioDeafult(SQLiteDatabase db) {
        ContentValues activo = new ContentValues();
        activo.put(TablaEstadoUsuario.estado, "Activo");
        db.insert(TablaEstadoUsuario.tableName, null, activo);

        ContentValues inactivo = new ContentValues();
        inactivo.put(TablaEstadoUsuario.estado, "Inactivo");
        db.insert(TablaEstadoUsuario.tableName, null, inactivo);

    }

    public void crearRolesUsuariosDefault(SQLiteDatabase db) {

        ContentValues rolAdministrador = new ContentValues();
        rolAdministrador.put(TablaRolUsuario.rol, "Administrador");
        db.insert(TablaRolUsuario.tableName, null, rolAdministrador);

        ContentValues rolAsistente = new ContentValues();
        rolAsistente.put(TablaRolUsuario.rol, "Asistente");
        db.insert(TablaRolUsuario.tableName, null, rolAsistente);

        ContentValues rolUsuario = new ContentValues();
        rolUsuario.put(TablaRolUsuario.rol, "Usuario");
        db.insert(TablaRolUsuario.tableName, null, rolUsuario);
    }


    public void crearCanchaDefault(SQLiteDatabase db) {

        ContentValues cancha1 = new ContentValues();
        cancha1.put(TablaCancha.nombre, "cancha de futbol 5");
        cancha1.put(TablaCancha.descripcion, "cancha de cemento");
        cancha1.put(TablaCancha.horaInicio, "2");
        cancha1.put(TablaCancha.horaFin, "4");
        cancha1.put(TablaCancha.idEstado, 1);
        cancha1.put(TablaCancha.idTipoCancha, 1);
        cancha1.put(TablaCancha.idEdificio, 1);

        db.insert(TablaCancha.tableName, null, cancha1);

    }


    public void crearEstadoCanchaDeafult(SQLiteDatabase db) {
        ContentValues activo = new ContentValues();
        activo.put(TablaEstadoCancha.estado, "Activo");
        db.insert(TablaEstadoCancha.tableName, null, activo);

        ContentValues inactivo = new ContentValues();
        inactivo.put(TablaEstadoCancha.estado, "Inactivo");
        db.insert(TablaEstadoCancha.tableName, null, inactivo);

    }

    public void crearEdificioDefault(SQLiteDatabase db) {

        ContentValues polideportivo = new ContentValues();
        polideportivo.put(TablaEdificio.nombre, "polideportivo");
        polideportivo.put(TablaEdificio.descripcion, "edificio deportivo");
        polideportivo.put(TablaEdificio.direccion, "calle");
        polideportivo.put(TablaEdificio.idEstado,1);


        db.insert(TablaEdificio.tableName, null, polideportivo);

    }
    public void crearTipoCanchaDeafult(SQLiteDatabase db) {
        ContentValues futbol = new ContentValues();
        futbol.put(TablaTipoCancha.tipo, "futbol");
        db.insert(TablaTipoCancha.tableName, null, futbol);

        ContentValues basquet = new ContentValues();
        basquet.put(TablaTipoCancha.tipo, "basquet");
        db.insert(TablaTipoCancha.tableName, null, basquet);

    }
    public void crearEstadoEdificioDeafult(SQLiteDatabase db) {
        ContentValues activo = new ContentValues();
        activo.put(TablaEstadoEdificio.estado, "Activo");
        db.insert(TablaEstadoEdificio.tableName, null, activo);

        ContentValues inactivo = new ContentValues();
        inactivo.put(TablaEstadoEdificio.estado, "Inactivo");
        db.insert(TablaEstadoEdificio.tableName, null, inactivo);

    }

}