/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.entidades.Aplicacion;
import java.util.List;

/**
 *
 * @author MARTIN
 */
public interface AplicacionDao {
    List<Aplicacion> getAplicacionesDisponibles(int idUsuario);
    List<Aplicacion> getAplicacionesDisponiblesFiltradas(int idUsuario, String filtro);
    boolean comprarAplicacion(int idUsuario, int idApp);
    boolean existeCompra(int idUsuario, int idApp);
    boolean existeApp(int idUsuario, String appNom);
    boolean crearAplicacion(int idUsuario, String appNom, String precio);

    boolean clasificarApp(int idUsuario, int idApp, int idCalificacion);
}
