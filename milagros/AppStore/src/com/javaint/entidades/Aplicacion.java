/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

import com.javaint.excepciones.DatoInvalidoException;
import com.javaint.excepciones.RequeridoException;

/**
 *
 * @author MARTIN
 */
public class Aplicacion {

    private Integer idApp;
    private String nombre;
    private String descripcion;
    private Float precio;
    private Usuario usuario;
    private Calificacion calificacion;

    public Aplicacion(int idApp) {
        this.idApp = idApp;
    }

    public Aplicacion(int idApp, String nombre, float precio) {
        this.idApp = idApp;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Aplicacion(String nombre, float precio, String descripcion) {
        this.idApp = 0;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Integer getIdApp() {
        return idApp;
    }

    public void setIdApp(Integer idApp) {
        this.idApp = idApp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }
    
    public void validar() throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new RequeridoException("nombre");
        }
        if (!(nombre.length() < 45 && nombre.length() > 0)) {
            throw new DatoInvalidoException("nombre");
        }
        if (!descripcion.isEmpty() && !(descripcion.length() < 200 && descripcion.length() > 0)) {
            throw new DatoInvalidoException("descripcion");
        }
        if ((precio <= 0)) {
            throw new DatoInvalidoException("precio");
        }
    }
    
    public void calificar(Calificacion c) throws Exception {
        if (c == null || c.equals(Calificacion.SIN_CALIFICAR)) {
            throw new DatoInvalidoException("calificacion");
        }
        if(this.calificacion.equals(Calificacion.SIN_CALIFICAR)){
            this.calificacion = c;
            return;
        }
        this.calificacion.promediar(c);        
    }
}
