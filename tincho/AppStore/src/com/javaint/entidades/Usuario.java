/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

import java.io.Serializable;

/**
 * @author MARTIN
 */
public class Usuario implements Serializable {

    private int idUsuario;
    private String nombre;
    private Password pass;

    public Usuario(String nombre, String pass) {
        this(0, nombre, pass);
    }

    public Usuario(int id, String nombre, String pass) {
        this.idUsuario = id;
        this.nombre = nombre;
        this.pass = new Password(pass);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Password getPass() {
        return pass;
    }

    public void setPass(Password pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Usuario: " + this.getNombre();
    }

    public boolean validatePass(String pass) {
        return this.pass.validate(pass);
    }

}
