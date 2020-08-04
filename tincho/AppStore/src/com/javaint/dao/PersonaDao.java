package com.javaint.dao;

import com.javaint.entidades.Persona;

public interface PersonaDao {
    boolean crear(Persona p);

    boolean existeEmail(String email);

    Persona obtenerPersonaXidUsuario(int idUsuario);

    boolean editar(Persona persona);

    Persona validate(String user, String pass);
}
