/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.entidades.Usuario;

import java.sql.Connection;

/**
 * @author MARTIN
 */
public interface UsuarioDao {

    Usuario create(Usuario u);

    Usuario validate(String nombre, String pass);

    Usuario obtenerXidUsuario(Integer idUsuario);

    boolean editar( Usuario usuario);
}
