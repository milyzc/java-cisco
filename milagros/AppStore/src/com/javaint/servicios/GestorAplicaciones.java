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
    private CalificacionDao calificacionDAO;

    public GestorAplicaciones(Usuario usuario) {
        this.appDAO = new AplicacionDaoJDBC();
        this.usuario = usuario;
    }
    
    public List<Aplicacion> buscarAplicacionesDisponibles(){
        return this.appDAO.getAplicacionesDisponibles(this.usuario.getId());
    }
    
    public List<Aplicacion> buscarAplicacionesDisponibles(String name){
        return this.appDAO.getAplicacionesDisponiblesFiltradas(this.usuario.getId(), name);
    }
    
    public void publicar(String nombre, float precio, String descripcion) throws Exception {
        Aplicacion app = new Aplicacion(nombre, precio, descripcion);
        app.validar();
        app.setUsuario(this.usuario);
        this.appDAO.publicar(app);
    }    
    
    public void comprar(Integer idApp) throws Exception {
        Aplicacion app = new Aplicacion(idApp);
        app.setUsuario(this.usuario);
        this.appDAO.comprar(app);
    }
    
    
    public boolean esAppComprada(Integer idApp) {
        return this.appDAO.esAppComprada(idApp, this.usuario.getId());
    }
    
    public List<Calificacion> buscarCalificacionesDisponibles(){
        this.calificacionDAO = new CalificacionDaoJDBC();
        return this.calificacionDAO.get();
    }
}
