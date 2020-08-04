/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

import com.javaint.excepciones.DatoInvalidoException;
import com.javaint.excepciones.RequeridoException;
import java.io.Serializable;

/**
 *
 * @author MARTIN
 */
public class Usuario implements Serializable {

    private Integer id;
    private String nombre;
    private Password pass;
    private Persona persona;
    
    public Usuario(String nombre, String pass){
        this(0, nombre, pass);
    }

    public Usuario(int id, String nombre, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.pass = new Password(pass);
    }
       
    public Usuario(String pass, Persona persona) {        
        this.id = 0;
        this.persona = persona;
        this.nombre = persona.getEmail();
        this.pass = new Password(pass);
    }

    public Integer getId() {
        return id;
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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }   
    
    
    @Override
    public String toString() {
        return "Usuario: " + this.getNombre();
    }

    public boolean validatePass(String pass) {
        return this.pass.validate(pass);
    }
    
    public void validar() throws Exception {
        if(nombre == null || nombre.isEmpty()){
            throw new RequeridoException("nombre de usuario");
        }
        if(!(nombre.length() < 45 && nombre.length() > 0)){
            throw new DatoInvalidoException("nombre de usuario");
        }
        if(!pass.esFuerte())
            throw new RuntimeException("La contraseña debe contener al menos una mayúscula, una minúscula y un número");
    }
}
