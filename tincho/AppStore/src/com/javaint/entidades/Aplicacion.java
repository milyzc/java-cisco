/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

/**
 *
 * @author MARTIN
 */
public class Aplicacion {
      private int idApp;
      private String nombre;
      private float precio;
      private Calificacion calificacion;

    public Aplicacion(int idApp, String nombre, float precio,Calificacion calificacion) {
        this.idApp = idApp;
        this.nombre = nombre;
        this.precio = precio;
        this.calificacion = calificacion;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }
}
