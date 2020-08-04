package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Calificacion;
import com.javaint.entidades.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CalificacionDaoJDBC implements CalificacionDao{
    private Configuracion config;

    public CalificacionDaoJDBC() {
        this.config = Configuracion.getInstance();
    }

    @Override
    public List<Calificacion> obtenerCalificaciones() {
        List<Calificacion> aux = new LinkedList<>();
        String query = "SELECT * FROM calificaciones";
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(),
                config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Calificacion cal = new Calificacion(rs.getInt(1),
                        rs.getString(2));
                aux.add(cal);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!");
        }
        return aux;
    }

    @Override
    public Calificacion obtenerCalificacion(int idUsuario, int idApp) {
        String query = "SELECT c.* FROM calificaciones c, aplicaciones_compradas ac " +
                "WHERE ac.id_usuario = ? AND ac.id_aplicacion = ? AND ac.id_calificacion=c.id_calificacion";
        Calificacion aux = null;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idApp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = new Calificacion(
                        rs.getInt(1),
                        rs.getString(2));
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }
}
