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
    public List<Aplicacion> getAplicacionesDisponibles(int idUsuario);
    public List<Aplicacion> getAplicacionesDisponiblesFiltradas(int idUsuario, String filtro);
    public Aplicacion getAplicacionById(int id);
    public void publicar(Aplicacion app) throws Exception;
    public void comprar(Aplicacion app) throws Exception;
    public Aplicacion esAppComprada(Integer idApp, Integer idUsuario);
    public void calificar(Aplicacion app) throws Exception;
    
}
