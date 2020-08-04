package com.javaint.entidades;

import java.io.Serializable;

public class Calificacion implements Serializable {

    private int idCalificacion;
    private String nombre;

    public Calificacion(int idCalificacion, String nombre) {
        this.idCalificacion = idCalificacion;
        this.nombre = nombre;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
