package com.example.databases.db;


//Clase para creacion de tablas y relaciones entre tablas , sentencias DML (Data Definition Language)
public class DdlDb {

    //Crea la estructura de la tabla de usuarios
    protected  String createTableRolUsuario(){
        StringBuilder rolUsuario =  new StringBuilder();
        rolUsuario.append("CREATE TABLE "+ ContratoReservas.TablaRolUsuario.tableName+" (" );
        rolUsuario.append( ContratoReservas.TablaRolUsuario.idRolUsuario+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        rolUsuario.append( ContratoReservas.TablaRolUsuario.rol + " TEXT   "  );
        rolUsuario.append(" )");
        return rolUsuario.toString();
    }

    protected String createTableEstadoUsuario(){
        StringBuilder estadoUsuario  = new StringBuilder();
        estadoUsuario.append("CREATE TABLE "+ ContratoReservas.TablaEstadoUsuario.tableName+" (" );
        estadoUsuario.append( ContratoReservas.TablaEstadoUsuario.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoUsuario.append( ContratoReservas.TablaEstadoUsuario.estado + " TEXT   "  );
        estadoUsuario.append(" )");
        return estadoUsuario.toString();
    }

    protected String createTableEstadoHistorico(){
        StringBuilder estadoHistorico =  new StringBuilder();
        estadoHistorico.append("CREATE TABLE "+ ContratoReservas.TablaEstadoHistorico.tableName+" (" );
        estadoHistorico.append( ContratoReservas.TablaEstadoHistorico.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoHistorico.append( ContratoReservas.TablaEstadoHistorico.estado + " TEXT    ) "  );
        return estadoHistorico.toString();
    }

    protected String createTableTipoReservacion(){
        StringBuilder tipoReservacion =  new StringBuilder();
        tipoReservacion.append("CREATE TABLE "+ ContratoReservas.TablaTipoReservacion.tableName+" (" );
        tipoReservacion.append( ContratoReservas.TablaTipoReservacion.idTipoReservacion+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        tipoReservacion.append( ContratoReservas.TablaTipoReservacion.tipo + " TEXT    ) "  );
        return tipoReservacion.toString();
    }


    protected String createTableEstadoReservacion(){
        StringBuilder estadoReservacion =  new StringBuilder();
        estadoReservacion.append("CREATE TABLE "+ ContratoReservas.TablaEstadoReservacion.tableName+" (" );
        estadoReservacion.append( ContratoReservas.TablaEstadoReservacion.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoReservacion.append( ContratoReservas.TablaEstadoReservacion.estado+ " TEXT    ) "  );
        return estadoReservacion.toString();
    }


    protected String createTableEstadoEdificio(){
        StringBuilder estadoEdificio =  new StringBuilder();
        estadoEdificio.append("CREATE TABLE "+ ContratoReservas.TablaEstadoEdificio.tableName+" (" );
        estadoEdificio.append( ContratoReservas.TablaEstadoEdificio.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoEdificio.append( ContratoReservas.TablaEstadoEdificio.estado+ " TEXT    ) "  );
        return estadoEdificio.toString();
    }


    protected String createTableEstadoImagen(){
        StringBuilder estadoImagen =  new StringBuilder();
        estadoImagen.append("CREATE TABLE "+ ContratoReservas.TablaEstadoImagen.tableName+" (" );
        estadoImagen.append( ContratoReservas.TablaEstadoImagen.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoImagen.append( ContratoReservas.TablaEstadoImagen.estado+ " TEXT    ) "  );
        return estadoImagen.toString();
    }

    protected String createTableEstadoCancha(){
        StringBuilder estadoCancha =  new StringBuilder();
        estadoCancha.append("CREATE TABLE IF NOT EXISTS "+ ContratoReservas.TablaEstadoCancha.tableName+" (" );
        estadoCancha.append( ContratoReservas.TablaEstadoCancha.idEstado+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        estadoCancha.append( ContratoReservas.TablaEstadoCancha.estado+ " TEXT    ) "  );
        return estadoCancha.toString();
    }


    protected String createTableTipoCancha(){
        StringBuilder tipoCancha =  new StringBuilder();
        tipoCancha.append("CREATE TABLE "+ ContratoReservas.TablaTipoCancha.tableName+" (" );
        tipoCancha.append( ContratoReservas.TablaTipoCancha.idTipoCancha+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        tipoCancha.append( ContratoReservas.TablaTipoCancha.tipo + " TEXT    ) "  );
        return tipoCancha.toString();
    }


    protected String createTableUsuarios(){
        StringBuilder usuario = new StringBuilder();
        usuario.append("CREATE TABLE "+ ContratoReservas.TablaUsuario.tableName+" (" );
        usuario.append( ContratoReservas.TablaUsuario.idusuario+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        usuario.append( ContratoReservas.TablaUsuario.usuario + " TEXT    , "  );
        usuario.append( ContratoReservas.TablaUsuario.nombreCompleto + " TEXT    , "  );
        usuario.append( ContratoReservas.TablaUsuario.carnet + " TEXT , "  );
        usuario.append( ContratoReservas.TablaUsuario.correo + " TEXT    , "  );
        usuario.append( ContratoReservas.TablaUsuario.telefono + " TEXT , "  );
        usuario.append( ContratoReservas.TablaUsuario.password + " TEXT    , "  );
        usuario.append( ContratoReservas.TablaUsuario.idRol + " INTEGER , "  );
        usuario.append( ContratoReservas.TablaUsuario.idEstado + " INTEGER , "  );
        usuario.append( ContratoReservas.TablaUsuario.fechaCreacion + " TEXT    , "  );
        usuario.append("  FOREIGN KEY ("+ ContratoReservas.TablaUsuario.idRol+") REFERENCES "+ ContratoReservas.TablaRolUsuario.tableName+"("+ ContratoReservas.TablaRolUsuario.idRolUsuario+") , ");
        usuario.append("  FOREIGN KEY ("+ ContratoReservas.TablaUsuario.idEstado+") REFERENCES "+ ContratoReservas.TablaEstadoUsuario.tableName+"("+ ContratoReservas.TablaEstadoUsuario.idEstado+")");
        usuario.append(" )");
        return usuario.toString();
    }

    protected String createTableEdificio(){
        StringBuilder edificio = new StringBuilder();
        edificio.append("CREATE TABLE "+ ContratoReservas.TablaEdificio.tableName+" (" );
        edificio.append( ContratoReservas.TablaEdificio.idEdificio+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        edificio.append( ContratoReservas.TablaEdificio.nombre + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaEdificio.direccion + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaEdificio.descripcion + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaEdificio.fechaCreacion + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaEdificio.idEstado + " INTEGER , "  );
        edificio.append("  FOREIGN KEY ("+ ContratoReservas.TablaEdificio.idEstado+") REFERENCES "+ ContratoReservas.TablaEstadoEdificio.tableName+"("+ ContratoReservas.TablaEstadoEdificio.idEstado+")");
        edificio.append(" )");
        return edificio.toString();
    }

    protected String createTableImagen(){
        StringBuilder edificio = new StringBuilder();
        edificio.append("CREATE TABLE "+ ContratoReservas.TablaImagen.tableName+" (" );
        edificio.append( ContratoReservas.TablaImagen.idImagen+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        edificio.append( ContratoReservas.TablaImagen.imagen + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaImagen.nombre + " TEXT    , "  );
        edificio.append( ContratoReservas.TablaImagen.idEdificio + " INTEGER , "  );
        edificio.append( ContratoReservas.TablaImagen.idCancha + " INTEGER , "  );
        edificio.append("  FOREIGN KEY ("+ ContratoReservas.TablaImagen.idEdificio +") REFERENCES "+ ContratoReservas.TablaEdificio.tableName+"("+ ContratoReservas.TablaEdificio.idEdificio+") , ");
        edificio.append("  FOREIGN KEY ("+ ContratoReservas.TablaImagen.idCancha +") REFERENCES "+ ContratoReservas.TablaCancha.idCancha+"("+ ContratoReservas.TablaCancha.idCancha+")");
        edificio.append(" )");
        return edificio.toString();
    }


    protected  String createTableCancha(){
        StringBuilder cancha = new StringBuilder();
        cancha.append("CREATE TABLE "+ ContratoReservas.TablaCancha.tableName+" (" );
        cancha.append( ContratoReservas.TablaCancha.idCancha+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        cancha.append( ContratoReservas.TablaCancha.nombre + " TEXT    , "  );
        cancha.append( ContratoReservas.TablaCancha.descripcion + " TEXT    , "  );
        cancha.append( ContratoReservas.TablaCancha.telefonoContacto + " TEXT  , "  );
        cancha.append( ContratoReservas.TablaCancha.horaInicio + " TEXT    , "  );
        cancha.append( ContratoReservas.TablaCancha.horaFin + " TEXT    , "  );
        cancha.append( ContratoReservas.TablaCancha.idEdificio + " INTEGER , "  );
        cancha.append( ContratoReservas.TablaCancha.idTipoCancha + " INTEGER , "  );
        cancha.append( ContratoReservas.TablaCancha.idEstado + " INTEGER , "  );
        cancha.append("  FOREIGN KEY ("+ ContratoReservas.TablaCancha.idEdificio +") REFERENCES "+ ContratoReservas.TablaEdificio.tableName+"("+ ContratoReservas.TablaEdificio.idEdificio+") , ");
        cancha.append("  FOREIGN KEY ("+ ContratoReservas.TablaCancha.idTipoCancha +") REFERENCES "+ ContratoReservas.TablaTipoCancha.tableName+"("+ ContratoReservas.TablaTipoCancha.idTipoCancha+") , ");
        cancha.append("  FOREIGN KEY ("+ ContratoReservas.TablaCancha.idEstado +") REFERENCES "+ ContratoReservas.TablaEstadoCancha.idEstado+"("+ ContratoReservas.TablaEstadoCancha.idEstado+")  ");
        cancha.append(" )");
        return cancha.toString();
    }

    protected String createTableReservacion(){
        StringBuilder reserva = new StringBuilder();
        reserva.append("CREATE TABLE "+ ContratoReservas.TablaReserva.tableName+" (" );
        reserva.append( ContratoReservas.TablaReserva.idReservacion+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        reserva.append( ContratoReservas.TablaReserva.numReservacion + " TEXT    , "  );
        reserva.append( ContratoReservas.TablaReserva.fechaHoraCreacion + " TEXT    , "  );
        reserva.append( ContratoReservas.TablaReserva.fechaReservacion + " TEXT    , "  );
        reserva.append( ContratoReservas.TablaReserva.CodigoQR + " TEXT    , "  );
        reserva.append( ContratoReservas.TablaReserva.idCancha + " INTEGER , "  );
        reserva.append( ContratoReservas.TablaReserva.idEstado + " INTEGER , "  );
        reserva.append( ContratoReservas.TablaReserva.idTipoReservacion + " INTEGER , "  );
        reserva.append("  FOREIGN KEY ("+ ContratoReservas.TablaReserva.idCancha +") REFERENCES "+ ContratoReservas.TablaCancha.tableName+"("+ ContratoReservas.TablaCancha.idCancha+") , ");
        reserva.append("  FOREIGN KEY ("+ ContratoReservas.TablaReserva.idEstado +") REFERENCES "+ ContratoReservas.TablaEstadoReservacion.tableName+"("+ ContratoReservas.TablaEstadoReservacion.idEstado+") , ");
        reserva.append("  FOREIGN KEY ("+ ContratoReservas.TablaReserva.idTipoReservacion+") REFERENCES "+ ContratoReservas.TablaTipoReservacion.tableName+"("+ ContratoReservas.TablaTipoReservacion.idTipoReservacion +")  ");
        reserva.append(" )");
        return reserva.toString();
    }

    protected  String createTableHistorico(){
        StringBuilder historico = new StringBuilder();
        historico.append("CREATE TABLE "+ ContratoReservas.TablaHistoricoReservacion.tablaName+" (" );
        historico.append( ContratoReservas.TablaHistoricoReservacion.idHistorico+" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        historico.append( ContratoReservas.TablaHistoricoReservacion.fechaHoraEvento + " TEXT    , "  );
        historico.append( ContratoReservas.TablaHistoricoReservacion.idUsuario + " INTEGER , "  );
        historico.append( ContratoReservas.TablaHistoricoReservacion.idReservacion + " INTEGER , "  );
        historico.append( ContratoReservas.TablaHistoricoReservacion.idEstado + " INTEGER , "  );
        historico.append("  FOREIGN KEY ("+ ContratoReservas.TablaHistoricoReservacion.idEstado +") REFERENCES "+ ContratoReservas.TablaEstadoHistorico.tableName+"("+ ContratoReservas.TablaEstadoHistorico.idEstado+") , ");
        historico.append("  FOREIGN KEY ("+ ContratoReservas.TablaHistoricoReservacion.idReservacion +") REFERENCES "+ ContratoReservas.TablaReserva.tableName+"("+ ContratoReservas.TablaReserva.idReservacion +") , ");
        historico.append("  FOREIGN KEY ("+ ContratoReservas.TablaHistoricoReservacion.idUsuario+") REFERENCES "+ ContratoReservas.TablaUsuario.tableName+"("+ ContratoReservas.TablaUsuario.idusuario +")  ");
        historico.append(" )");
        return historico.toString();
    }

    protected  String createTableRestricciones(){
        StringBuilder restricciones = new StringBuilder();
        restricciones.append("CREATE TABLE "+ ContratoReservas.TablaRestricciones.tableName+" (" );
        restricciones.append( ContratoReservas.TablaRestricciones.idRestriccion +" INTEGER PRIMARY KEY AUTOINCREMENT  , ");
        restricciones.append( ContratoReservas.TablaRestricciones.idCancha +" INTEGER   , ");
        restricciones.append( ContratoReservas.TablaRestricciones.fechaRestriccion +" TEXT  , ");
        restricciones.append( ContratoReservas.TablaRestricciones.horaInicio +" TEXT  , ");
        restricciones.append( ContratoReservas.TablaRestricciones.horaFin +" TEXT  , ");
        restricciones.append( ContratoReservas.TablaRestricciones.causaRestriccion +" TEXT  , ");
        restricciones.append("  FOREIGN KEY ("+ ContratoReservas.TablaRestricciones.idCancha +") REFERENCES "+ ContratoReservas.TablaCancha.tableName+"("+ ContratoReservas.TablaCancha.idCancha+")  )");
        return restricciones.toString();
    }

    protected String dropTableRolUsuario(){
        StringBuilder rolUsuario = new StringBuilder();
        rolUsuario.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaRolUsuario.tableName);
        return rolUsuario.toString();
    }
    protected String dropTableEstadoUsuario(){
        StringBuilder estadoUsuario = new StringBuilder();
        estadoUsuario.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoUsuario.tableName);
        return estadoUsuario.toString();
    }
    protected String dropTableEstadoHistorico(){
        StringBuilder estadoHistorico = new StringBuilder();
        estadoHistorico.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoHistorico.tableName);
        return estadoHistorico.toString();
    }
    protected String dropTableTipoReservacion(){
        StringBuilder tipoReservacion = new StringBuilder();
        tipoReservacion.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaTipoReservacion.tableName);
        return tipoReservacion.toString();
    }
    protected String dropTableEstadoReservacion(){
        StringBuilder estadoReservacion = new StringBuilder();
        estadoReservacion.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoReservacion.tableName);
        return estadoReservacion.toString();
    }
    protected String dropTableEstadoEdificio(){
        StringBuilder estadoEdificio = new StringBuilder();
        estadoEdificio.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoEdificio.tableName);
        return estadoEdificio.toString();
    }
    protected String dropTableEstadoImagen(){
        StringBuilder estadoImagen = new StringBuilder();
        estadoImagen.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoImagen.tableName);
        return estadoImagen.toString();
    }
    protected String dropTableEstadoCancha(){
        StringBuilder estadoCancha = new StringBuilder();
        estadoCancha.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEstadoCancha.tableName);
        return estadoCancha.toString();
    }
    protected String dropTableTipoCancha(){
        StringBuilder tipoCancha = new StringBuilder();
        tipoCancha.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaTipoCancha.tableName);
        return tipoCancha.toString();
    }
    protected String dropTableUsuarios(){
        StringBuilder usuario = new StringBuilder();
        usuario.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaUsuario.tableName);
        return usuario.toString();
    }
    protected String dropTableEdificio(){
        StringBuilder edificio = new StringBuilder();
        edificio.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaEdificio.tableName);
        return edificio.toString();
    }
    protected String dropTableImagen(){
        StringBuilder imagen = new StringBuilder();
        imagen.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaImagen.tableName);
        return imagen.toString();
    }
    protected String dropTableCancha(){
        StringBuilder cancha = new StringBuilder();
        cancha.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaCancha.tableName);
        return cancha.toString();
    }
    protected String dropTableReservacion(){
        StringBuilder reservacion = new StringBuilder();
        reservacion.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaReserva.tableName);
        return reservacion.toString();
    }
    protected String dropTableHistorico(){
        StringBuilder historico = new StringBuilder();
        historico.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaHistoricoReservacion.tablaName);
        return historico.toString();
    }
    protected String dropTableRestricciones(){
        StringBuilder restricciones = new StringBuilder();
        restricciones.append("DROP TABLE IF EXISTS "+ ContratoReservas.TablaRestricciones.tableName);
        return restricciones.toString();
    }


}
