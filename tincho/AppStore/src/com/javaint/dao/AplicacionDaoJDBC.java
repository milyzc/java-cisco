/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Calificacion;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MARTIN
 */
public class AplicacionDaoJDBC implements AplicacionDao {

    private Configuracion config;
    private CalificacionDao calificacionDao;

    public AplicacionDaoJDBC() {
        this.config = Configuracion.getInstance();
        this.calificacionDao= new CalificacionDaoJDBC();
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
                        rs.getFloat(3),
                        calificacionDao.obtenerCalificacionXID(rs.getInt(4)));
                aux.add(app);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!", e);
        }

        return aux;
    }

    @Override
    public List<Aplicacion> buscarAplicacionesCompradas(int idUsuario) {
        List<Aplicacion> aux = new LinkedList<>();

        String query = "SELECT a.* "
                + " FROM aplicaciones  a,  aplicaciones_compradas ac"
                + " WHERE ac.id_aplicacion = a.id_aplicacion"
                + " AND ac.id_usuario = ?";
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(),
                config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            Aplicacion app;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        calificacionDao.obtenerCalificacionXID(rs.getInt(4)));
                aux.add(app);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!");
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

            Aplicacion app;
            while (rs.next()) {
                app = new Aplicacion(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        calificacionDao.obtenerCalificacionXID(rs.getInt(4)));
                aux.add(app);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar las aplicaciones disponibles!");
        }
        return aux;
    }

    @Override
    public boolean comprarAplicacion(int idUsuario, int idApp) {
        int rows;
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(),
                config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            rows = stm.executeUpdate("INSERT INTO aplicaciones_compradas (id_usuario," +
                    " id_aplicacion) VALUES('"
                    + idUsuario + "','" +
                    idApp + "')");

        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar la Compra!", sqle);
        }
        return rows == 1;
    }

    @Override
    public boolean existeCompra(int idUsuario, int idApp) {
        String query = "SELECT * FROM aplicaciones_compradas WHERE id_usuario = ? AND id_aplicacion = ?";
        boolean aux = false;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idApp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = true;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }

    @Override
    public boolean existeApp(int idUsuario, String appNom) {
        String query = "SELECT * FROM aplicaciones WHERE UPPER(nombre) like UPPER(?)";
        boolean aux = false;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, appNom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = true;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }

    @Override
    public boolean crearAplicacion(int idUsuario, String appNom, String precio) {
        int rows;
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(),
                config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            cnn.setAutoCommit(false);
            rows = stm.executeUpdate("INSERT INTO aplicaciones (nombre," +
                    " precio) VALUES('"
                    + appNom + "','" +
                    precio + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int idApp = rs.getInt(1);
                crearAplicacionUsuario(idUsuario, idApp);
            }
            cnn.commit();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar la Aplicación!", sqle);
        }
        return rows == 1;
    }

    private void crearAplicacionUsuario(int idUsuario, int idApp) {
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(),
                config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            stm.executeUpdate("INSERT INTO aplicaciones_usuarios (id_usuario," +
                    " id_aplicacion) VALUES('"
                    + idUsuario + "','" +
                    idApp + "')", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar la Aplicación x Usuario!", sqle);
        }
    }

    @Override
    public boolean clasificarApp(int idUsuario, int idApp, int idCalificacion) {
        int rows;
        String query = "UPDATE aplicaciones_compradas SET id_calificacion=? where id_usuario=? AND id_aplicacion=?";
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement stm = cnn.prepareStatement(query)) {
            stm.setInt(1, idCalificacion);
            stm.setInt(2, idUsuario);
            stm.setInt(3, idApp);
            rows = stm.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo clasificar!", sqle);
        }
        actualizarCalificacionGral(idApp);
        return rows == 1;
    }

    private void actualizarCalificacionGral(int idApp) {

        String query = "UPDATE aplicaciones " +
                "SET id_calificacion=? where id_aplicacion=?";
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement stm = cnn.prepareStatement(query)) {
            stm.setInt(1, obtenercalificacionPromedio(idApp) );
            stm.setInt(2,idApp);
            stm.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo clasificar!", sqle);
        }
    }

    private int obtenercalificacionPromedio(int idApp) {
        String query = "SELECT Avg(id_calificacion) " +
                "FROM aplicaciones_compradas" +
                " WHERE id_aplicacion=? AND id_calificacion<>0";
        double aux = 0;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idApp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = rs.getDouble(1);
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return (int) Math.round(aux);
    }
}
