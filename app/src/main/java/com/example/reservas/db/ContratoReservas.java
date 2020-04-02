package com.example.reservas.db;

//Estructuras de las tablas de la base de datos , definiciones  de campos y nombres de columnas
public class ContratoReservas {

    public interface TablaEstadoUsuario {
        String tableName ="estado_usuario";
        String idEstado = "idEstado";
        String estado = "estado";
    }

    public interface TablaRolUsuario {
        String tableName ="rol_usuario";
        String idRolUsuario = "idRolUsuario";
        String rol = "rol";
    }

    public interface TablaTipoCancha {
        String tableName ="tipo_cancha";
        String idTipoCancha= "idTipoCancha";
        String tipo= "tipo";
    }

    public interface TablaEstadoCancha {
        String tableName = "estado_cancha";
        String idEstado = "idEstado";
        String estado = "estado";
    }

    public interface TablaEstadoEdificio {
        String tableName = "estado_edificion";
        String idEstado = "idEstado";
        String estado = "estado";
    }

    public interface TablaEdificio {
        String tableName  = "edificio";
        String idEdificio = "idEdificio";
        String nombre = "nombre";
        String direccion = "direccion";
        String idEstado = "idEstado";
        String descripcion = "descripcion";
        String fechaCreacion = "fechaCreacion";
    }

    public interface TablaUsuario {
        String tableName  = "usuario";
        String idusuario ="idusuario";
        String usuario ="usuario";
        String nombreCompleto ="nombreCompleto";
        String carnet ="carnet";
        String correo ="correo";
        String telefono ="telefono";
        String password ="password";
        String idRol ="idRol";
        String idEstado ="idEstado";
        String fechaCreacion ="fechaCreacion";
    }

    public interface TablaCancha {
        String tableName = "cancha";
        String idCancha = "idCancha";
        String nombre = "nombre";
        String descripcion = "descripcion";
        String telefonoContacto = "telefonoContacto";
        String horaInicio = "horaInicio";
        String horaFin = "horaFin";
        String idEdificio = "idEdificio";
        String idTipoCancha = "idTipoCancha";
        String idEstado = "idEstado";
        String fechaCreacion = "fechaCreacion";
    }

    public interface TablaEstadoImagen {
        String tableName= "estado_imagen";
        String idEstado = "idEstado";
        String estado = "estado";
    }

    public interface TablaImagen {
        String tableName = "imagen";
        String idImagen = "idImagen";
        String imagen = "imagen";
        String nombre = "nombre";
        String idCancha = "idCancha";
        String idEdificio = "idEdificio";
        String idEstado = "idEstado";
    }

    public interface TablaTipoReservacion {
        String tableName = "tipo_reservacion";
        String idTipoReservacion = "idTipoReservacion";
        String tipo = "tipo";
        String descripcion = "descripcion";
    }

    public interface TablaEstadoReservacion {
        String tableName =  "estado_reservacion";
        String idEstado ="idEstado";
        String estado ="estado";
    }

    public interface TablaRestricciones {
        String tableName =  "restricciones";
        String idRestriccion = "idRestriccion";
        String fechaRestriccion = "fechaRestriccion";
        String horaInicio = "horaInicio";
        String horaFin = "horaFin";
        String causaRestriccion = "causaRestriccion";
        String idCancha ="idCancha";
    }



    public interface TablaReserva {
        String tableName="reserva";
        String idReservacion = "idReservacion";
        String numReservacion = "numReservacion";
        String fechaHoraCreacion = "fechaHoraCreacion";
        String fechaReservacion = "fechaReservacion";
        String horaInicio = "horaInicio";
        String horaFin = "horaFin";
        String idCancha = "idCancha";
        String idEstado = "idEstado";
        String idTipoReservacion = "idTipoReservacion";
        String CodigoQR = "CodigoQR";
    }

    public interface TablaHistoricoReservacion {
        String tablaName="historico_reservacion";
        String idHistorico = "idHistorico";
        String fechaHoraEvento = "fechaHoraReservacion";
        String idUsuario = "idUsuario";
        String idReservacion = "idReservacion";
        String idEstado = "idEstado";
        String comentarios = "comentarios";
    }

    public interface TablaEstadoHistorico {
        String tableName = "estado_historico";
        String idEstado = "idEstado";
        String estado = "estado";

    }

}
