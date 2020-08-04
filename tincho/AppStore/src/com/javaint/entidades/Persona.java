package com.javaint.entidades;

import java.io.Serializable;

public class Persona implements Serializable {
    private int idPersona;
    private String apellidoNombre;
    private String dni;
    private String email;
    private Usuario usuario;

    public Persona(String apellidoNombre, String dni, String email, String usuario,String pass) {
        this.apellidoNombre = apellidoNombre;
        this.dni = dni;
        this.email = email;
        this.usuario = new Usuario(usuario,pass);
    }

    public Persona(int idPersona, String apellidoNombre,
                   String dni, String email, Usuario usuario) {
        this.idPersona=idPersona;
        this.apellidoNombre = apellidoNombre;
        this.dni = dni;
        this.email = email;
        this.usuario = usuario;
    }


    public int getIdPersona() {
        return idPersona;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
