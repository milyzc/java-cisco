/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Calificacion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mily_
 */
public class CalificacionDaoJDBC implements CalificacionDao {

    private Configuracion config;

    public CalificacionDaoJDBC() {
        this.config = Configuracion.getInstance();
    }

    @Override
    public List<Calificacion> get() {
        List<Calificacion> aux = new ArrayList<>();

        String query = "SELECT *  FROM calificaciones ";

        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            Calificacion c;
            while (rs.next()) {
                c = new Calificacion(rs.getInt(1), rs.getString(2));
                aux.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las calificaciones disponibles!", e);
        }

        return aux;
    }

}
