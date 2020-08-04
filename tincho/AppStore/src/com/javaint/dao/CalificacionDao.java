package com.javaint.dao;

import com.javaint.entidades.Calificacion;

import java.util.List;

public interface CalificacionDao {
    List<Calificacion> obtenerCalificaciones();

    Calificacion obtenerCalificacion(int idUsuario, int idApp);

    Calificacion obtenerCalificacionXID(int idClasificacion);
}
