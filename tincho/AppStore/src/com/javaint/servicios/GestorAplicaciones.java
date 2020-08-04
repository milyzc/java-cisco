/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.servicios;

import com.javaint.dao.AplicacionDao;
import com.javaint.dao.AplicacionDaoJDBC;
import com.javaint.dao.CalificacionDao;
import com.javaint.dao.CalificacionDaoJDBC;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Calificacion;
import com.javaint.entidades.Usuario;
import java.util.List;

/**
 *
 * @author george
 */
public class GestorAplicaciones {

    private final Usuario usuario;
    private final AplicacionDao appDAO;
    private final CalificacionDao calificacionDAO;

    public GestorAplicaciones(Usuario usuario) {
        this.appDAO = new AplicacionDaoJDBC();
        this.usuario = usuario;
        this.calificacionDAO = new CalificacionDaoJDBC();
    }
    
    public List<Aplicacion> buscarAplicacionesDisponibles(){
        return this.appDAO.getAplicacionesDisponibles(this.usuario.getIdUsuario());
    }
    
    public List<Aplicacion> buscarAplicacionesDisponibles(String name){
        return this.appDAO.getAplicacionesDisponiblesFiltradas(this.usuario.getIdUsuario(), name);
    }


    public boolean comprarAplicacion(int idUsuario, int idApp) {
        return this.appDAO.comprarAplicacion(idUsuario,idApp);
    }

    public boolean existeCompra(int idUsuario, int idApp) {
        return this.appDAO.existeCompra(idUsuario,idApp);
    }

    public boolean existeApp(int idUsuario, String appNom) {
        return this.appDAO.existeApp(idUsuario,appNom);
    }

    public boolean crearAplicacion(int idUsuario, String appNom,String precio) {
        return this.appDAO.crearAplicacion(idUsuario,appNom,precio);
    }

    public boolean clasificarApp(int idApp, int idUsuario, Calificacion calificacion) {
       return appDAO.clasificarApp(idUsuario,idApp,calificacion.getIdCalificacion());
    }
    public Calificacion obtenerCalificacion(int idUsuario, int idApp)
    {
        return calificacionDAO.obtenerCalificacion(idUsuario,idApp);
    }
    public List<Calificacion> obtenerCalificaciones() {
        return calificacionDAO.obtenerCalificaciones();
    }

    public List<Aplicacion> buscarAplicacionesCompradas() {
        return this.appDAO.buscarAplicacionesCompradas(this.usuario.getIdUsuario());
    }
}
