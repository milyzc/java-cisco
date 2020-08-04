/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.entidades.Persona;

/**
 *
 * @author mily_
 */
public interface PersonaDao {
    public Persona getByEmail(String email);
}
