/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.entidades;

import com.javaint.excepciones.DatoInvalidoException;
import com.javaint.excepciones.RequeridoException;
import java.util.Objects;

/**
 *
 * @author mily_
 */
public class Persona {
    private Integer id;
    private String nombreApellido;
    private String email;
    private String dni;

    public Persona(){
        
    }
    
    public Persona(String nombreApellido, String email, String dni) {
        this.nombreApellido = nombreApellido;
        this.email = email;
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.email);
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
        final Persona other = (Persona) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
    public void validar() throws Exception {
        if(nombreApellido == null || nombreApellido.isEmpty()){
            throw new RequeridoException("nombre y apellido");
        }
        if(email == null ||  email.isEmpty()){
            throw new RequeridoException("email");
        }
        if(dni == null || dni.isEmpty()){
            throw new RequeridoException("dni");
        }
        if(!(nombreApellido.length() < 45 && nombreApellido.length() > 0)){
            throw new DatoInvalidoException("nombre y apellido");
        }
        if(!(email.length() < 45 && email.length() > 0)){
            throw new DatoInvalidoException("email");
        }
        if(!(dni.length() < 10 && dni.length() > 0)){
            throw new DatoInvalidoException("dni");
        }
    }
    
}
