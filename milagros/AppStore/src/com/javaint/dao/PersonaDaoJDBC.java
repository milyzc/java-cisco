/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Persona;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mily_
 */
public class PersonaDaoJDBC implements PersonaDao{

    @Override
    public Persona getByEmail(String email) {
        String query = "SELECT id_persona FROM personas WHERE email = ?";
        Persona p = null;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Persona();
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return p;
    }
    
}
