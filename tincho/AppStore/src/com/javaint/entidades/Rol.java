package com.javaint.entidades;

import java.io.Serializable;

public class Rol implements Serializable {
    private int idRol;
    private String nombre;

    public int getIdRol() {
        return idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
