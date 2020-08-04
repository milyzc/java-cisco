/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

import java.util.Objects;

/**
 *
 * @author mily_
 */
public class Calificacion {
    public static final Calificacion SIN_CALIFICAR = new Calificacion(0);
    
    private Integer id;
    private String nombre;

    public Calificacion(Integer id) {
        this.id = id;
    }
    
    public Calificacion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void promediar(Calificacion c1){
        Double avg = (Double) Math.ceil((this.id + c1.getId())/2);
        this.id = (Integer) avg.intValue(); 
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Calificacion other = (Calificacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
        
    
    
}
