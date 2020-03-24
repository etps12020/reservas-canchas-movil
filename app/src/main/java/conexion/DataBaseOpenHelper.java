package conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {


    String sqlCreateTableEstadoUsuario = "CREATE TABLE estado_usuario(idEstado INTEGER PRIMARY KEY AUTOINCREMENT, estado TEXT)";
    String sqlCreateTableUsuarios = "CREATE TABLE usuario (idusuario INTEGER PRIMARY KEY AUTOINCREMENT ,  usuario TEXT , nombreCompleto TEXT ,  carnet TEXT  , correo TEXT  , telefono TEXT , password TEXT  , idRolUsuario INTEGER , idEstado INTERGER ,  \n" +
            "fechaCreacion TEXT ,  foreign key (idRolUsuario)  references rol_usuario(idRolUsuario) );";

    String sqlCreateTableRolUsuario = "CREATE TABLE rol_usuario(idRolUsuario INTEGER PRIMARY KEY AUTOINCREMENT, rol TEXT UNIQUE  );";


    String CrearTabla_tipoCancha = "create table tipo_cancha(idTipoCancha int primary key autoincrement, tipo text)";
    String CrearTabla_restriciones = "create table restricciones(idRestriciones int primary key autoincrement, fechaRestriccion text, horaInicio text, horaFin text, causaRestriccion text)";
    String CrearTabla_estadoCancha = "create table estado_cancha(idEstado int primary key autoincrement, estado text)";
    String CrearTabla_cancha = "create table cancha(idCancha int primary key autoincrement, nombre text, descripcion text, telefonoContacto text, horaInicio text, horaFin text, idEdificio int references edificio(idEdificio), idTipoCancha int references tipo_cancha(idTipoCancha), idEstado int references estado_cancha(idEstado), idRestricciones int references restricciones(idRestriciones), fechaCreacion text)";

    String CrearTabla_edificio = "create table edificio(idEdificio int primary key autoincrement, nombre text, direccion text, idEstado int references estado_edificio(idEstado), descripcion text, fechaCreacion text)";
    String CrearTabla_estadoEdificio= "create table estado_edificio(idEstado int primary key autoincrement, estado text)";

    String CrearTabla_estadoImagen= "create table estado_imagenes(idEstado int primary key autoincrement, estado text)";
    String CrearTabla_imagen = "create table imagenes(idImagenes, imagen text, nombre text, idCancha int references cancha(idCancha), idEdificio int references edificio(idEdificio), idEstado int references estado_edificio(idEstado))";

    String CrearTabla_reservacion = "create table reservacion(idReservacion int primary key autoincrement, numReservacion int, fechayHoraCrecion text, fechaReservacion text, idHoraReservar int references horario_reservacion(idHorarioReservacion), idCancha int references cancha(idCancha), idEstado int references estado_reservacion(idEstado), idTipoReservacion int references tipo_reservacion(idTipoReservacion), codigoQR text)";
    String CrearTabla_tipoReservacion = "create table tipo_reservacion(idTipoReservacion int primary key autoincrement, tipo text, descripcion text)";
    String CrearTabla_estadoReservacion= "create table estado_reservacion(idEstado int primary key autoincrement, estado text)";
    String CrearTabla_horarioReservacion = "create table horario_reservacion(idHorarioReservacion int primary key autoincrement, horaInicio text, horaFin text)";

    String CrearTabla_estadoHistorico= "create table estado_historico(idEstado int primary key autoincrement, estado text)";
    String CrearTala_historicoReservacion = "create table historico_reservacion(idHistorico int primary key autoincrement, fechayHoraEvento text, idUsuario int references usuario(idUsuario),idReservacion int references reservacion(idReservacion), idEstado int references estado_historico(idEstado), Comentarios text)";




    public DataBaseOpenHelper(Context context  , String name  , SQLiteDatabase.CursorFactory factory , int version){
        super(  context , name , factory ,  version  );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableEstadoUsuario);
        db.execSQL(sqlCreateTableRolUsuario);
        db.execSQL(sqlCreateTableUsuarios);


      db.execSQL(CrearTabla_reservacion);
      db.execSQL(CrearTabla_tipoReservacion);
       db.execSQL(CrearTabla_estadoReservacion);
      db.execSQL(CrearTabla_horarioReservacion);

      db.execSQL(CrearTabla_cancha);
        db.execSQL(CrearTabla_tipoCancha);
        db.execSQL(CrearTabla_restriciones);
        db.execSQL(CrearTabla_estadoCancha);

        db.execSQL(CrearTabla_edificio);
        db.execSQL(CrearTabla_estadoEdificio);

        db.execSQL(CrearTabla_imagen);
        db.execSQL(CrearTabla_estadoImagen);

        db.execSQL(CrearTabla_estadoHistorico);
        db.execSQL(CrearTala_historicoReservacion);



        ingresarEstadosUsuarios(db);
        ingresarRoles(db);
        ingresarUsuariosPorDefecto(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(sqlCreateTableUsuarios);
    }


    public void ingresarUsuariosPorDefecto(SQLiteDatabase  db){

        ContentValues administrador1 =  new ContentValues();
        ContentValues asistente1 =  new ContentValues();
        ContentValues  usuario1  = new ContentValues();

        administrador1.put("usuario" , "admin");
        administrador1.put("nombreCompleto" , "administrador");
        administrador1.put("carnet" , "2515642015");
        administrador1.put("correo" , "admin@mail.com");
        administrador1.put("password" , "1234");
        administrador1.put("idRolUsuario" , 1);
        administrador1.put("idEstado" , 1);

        asistente1.put("usuario" , "asistente");
        asistente1.put("nombreCompleto" , "Juan perez");
        asistente1.put("carnet" , "na");
        asistente1.put("correo" , "asistentez@mail.com");
        asistente1.put("password" , "1234");
        asistente1.put("idRolUsuario" , 2);
        asistente1.put("idEstado" , 1);

        usuario1.put("usuario" , "usuario1");
        usuario1.put("nombreCompleto" , "Jose Perez");
        usuario1.put("carnet" , "na");
        usuario1.put("correo" , "usuario@mail.com");
        usuario1.put("password" , "1234");
        usuario1.put("idRolUsuario" , 3);
        usuario1.put("idEstado" , 1);


        db.insert("usuario" , null  , administrador1);
        db.insert("usuario" , null   ,  asistente1);
        db.insert("usuario" ,   null   ,   usuario1  );
    }

    public void ingresarEstadosUsuarios(SQLiteDatabase db){

        ContentValues  estadoActivo   =  new ContentValues();
        estadoActivo.put("estado" , "Activo" );
        db.insert("estado_usuario" , null  , estadoActivo);


        ContentValues  estadoInactivo   =  new ContentValues();
        estadoInactivo.put("estado" , "Inactivo" );
        db.insert("estado_usuario" , null  , estadoInactivo);

    }

    public void ingresarRoles(SQLiteDatabase db){

        ContentValues  rolAdministrador  = new ContentValues();
        rolAdministrador.put("rol" , "administrador");
        db.insert("rol_usuario" , null  , rolAdministrador);


        ContentValues  rolAsistente  = new ContentValues();
        rolAsistente.put("rol" , "Asistente");
        db.insert("rol_usuario" , null  , rolAsistente);


        ContentValues  rolUsuario  = new ContentValues();
        rolUsuario.put("rol" , "UsuarioApp");
        db.insert("rol_usuario" , null  , rolUsuario);


    }
}
