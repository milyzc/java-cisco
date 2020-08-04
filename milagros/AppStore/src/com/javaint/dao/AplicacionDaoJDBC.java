/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Rol;
import com.javaint.excepciones.OperacionIncompletaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

        String query = "SELECT t.* "
                + " FROM aplicaciones  t,  aplicaciones_usuarios t1"
                + " WHERE t1.id_aplicacion = t.id_aplicacion "
                + " AND t.nombre LIKE ? "
                + " AND t1.id_usuario <> ?";

        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setString(1, "%" + filtro + "%");
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();

            Aplicacion app = null;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1), rs.getString(2), rs.getFloat(3));
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

        String query = "SELECT t.* "
                + " FROM aplicaciones  t,  aplicaciones_usuarios t1"
                + " WHERE t1.id_aplicacion = t.id_aplicacion"
                + " AND t1.id_usuario <> ?";

        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            Aplicacion app = null;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1), rs.getString(2), rs.getFloat(3));
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

    @Override
    public void publicar(Aplicacion app) throws Exception {
        Connection cnn;
        int rows = 0;
        String queryApp = "INSERT INTO aplicaciones (nombre, precio, descripcion) VALUES (?,?,?)";
        String queryAppUsuario = "INSERT INTO aplicaciones_usuarios (id_usuario, id_aplicacion) VALUES (?,?)";
        String queryUsuario = "UPDATE usuarios SET id_rol=? WHERE id_usuario=?";
        try {
            cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD, no se pudo conectar!", sqle);
        }
        try (PreparedStatement psApp = cnn.prepareStatement(queryApp);
             PreparedStatement psAppUsuario = cnn.prepareStatement(queryAppUsuario);
             PreparedStatement psUsuario = cnn.prepareStatement(queryUsuario);) {
            cnn.setAutoCommit(false);
            psApp.setString(1, app.getNombre());
            psApp.setString(2, app.getPrecio().toString());
            psApp.setString(3, app.getDescripcion());
            rows = psApp.executeUpdate();
            if (rows == 0) {
                throw new OperacionIncompletaException();
            }
            ResultSet rs = psApp.getGeneratedKeys();
            if (rs.next()) {
                app.setIdApp(rs.getInt(1));
            }
            psAppUsuario.setString(1, app.getUsuario().getId().toString());
            psAppUsuario.setString(2, app.getIdApp().toString());
            rows = psAppUsuario.executeUpdate();
            
            psUsuario.setString(1, Rol.DESARROLLADOR.toString());
            psAppUsuario.setString(2,app.getUsuario().getId().toString());
            rows = psAppUsuario.executeUpdate();
            cnn.commit();
        } catch (SQLIntegrityConstraintViolationException sic) {
            try {
                cnn.rollback();
                throw new RuntimeException("aplicación ya publicada");
            } catch (SQLException e) {
                throw new OperacionIncompletaException();
            }
        } catch (SQLException sqle) {
            try {
                cnn.rollback();
            } catch (SQLException e) {
                throw new OperacionIncompletaException();
            }
        } finally {
            try {
                cnn.close();
            } catch (SQLException e) {
            }
        }
        if (rows == 0) {
            throw new OperacionIncompletaException();
        }
    }

    @Override
    public void comprar(Aplicacion app) throws Exception {
        int rows = 0;
        String query = "INSERT INTO aplicaciones_compradas (id_usuario, id_aplicacion) VALUES (?,?)";
        try (Connection cnn = DriverManager
                .getConnection(config.getConnectionString(), config.getDbUserName(),
                        config.getDbPassword());
                PreparedStatement psApp = cnn.prepareStatement(query);) {
            psApp.setString(1, app.getUsuario().getId().toString());
            psApp.setString(2, app.getIdApp().toString());
            rows = psApp.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sic) {
            throw new RuntimeException("aplicación ya comprada");
        } catch (SQLException sqle) {
            throw new OperacionIncompletaException();
        }
        if (rows == 0) {
            throw new OperacionIncompletaException();
        }
    }

    @Override
    public boolean esAppComprada(Integer idApp, Integer idUsuario) {
        String query = "SELECT id_aplicacion FROM aplicaciones_compradas ac WHERE ac.id_usuario = ?";
        boolean esComprada = false;
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    esComprada = true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
        return esComprada;
    }

}
