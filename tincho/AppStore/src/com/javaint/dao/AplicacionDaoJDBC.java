/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Aplicacion;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MARTIN
 */
public class AplicacionDaoJDBC implements AplicacionDao {

    private Configuracion config;

    public AplicacionDaoJDBC() {
        this.config = Configuracion.getInstance();
    }

    @Override
    public List<Aplicacion> getAplicacionesDisponiblesFiltradas(int idUsuario, String filtro) {
        List<Aplicacion> aux = new LinkedList<>();

        String query = "SELECT a.* "
                + " FROM aplicaciones  a,  aplicaciones_usuarios au"
                + " WHERE au.id_aplicacion = a.id_aplicacion "
                + " AND a.nombre LIKE ? "
                + " AND au.id_usuario <> ?";

        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(),
                config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setString(1, "%" + filtro + "%");
            ps.setInt(2, idUsuario);
            
            ResultSet rs = ps.executeQuery();

            Aplicacion app;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3));
                aux.add(app);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!", e);
        }

        return aux;
    }

    @Override
    public List<Aplicacion> getAplicacionesDisponibles(int idUsuario) {
        List<Aplicacion> aux = new LinkedList<>();

        String query = "SELECT a.* "
                + " FROM aplicaciones  a,  aplicaciones_usuarios au"
                + " WHERE au.id_aplicacion = a.id_aplicacion"
                + " AND au.id_usuario <> ?";

        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(),
                config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            Aplicacion app = null;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3));
                aux.add(app);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!");
        }

        return aux;
    }

    @Override
    public Aplicacion getAplicacionById(int id) {
        return null;
    }

}
