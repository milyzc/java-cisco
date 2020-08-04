/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Usuario;

import java.sql.*;

/**
 * @author MARTIN
 */
public class UsuarioDaoJDBC implements UsuarioDao {

    private final Configuracion config;

    public UsuarioDaoJDBC() {
        this.config = Configuracion.getInstance();
    }

    @Override
    public boolean editar(Usuario usuario) {
        int rows;
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            rows = stm.executeUpdate("UPDATE usuarios SET nombre='" +
                    usuario.getNombre() +
                    "',password='" +
                    usuario.getPass().getValor() + "' where id_usuario = "+ usuario.getIdUsuario());
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo editar el usuario!", sqle);
        }
        return rows == 1;
    }

    @Override
    public Usuario obtenerXidUsuario(Integer idUsuario) {
        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Usuario aux = null;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = new Usuario(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }

    @Override
    public Usuario create(Usuario u) {
        Usuario usuario = null;
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            usuario = u;
            stm.executeUpdate("INSERT INTO usuarios (nombre, password) VALUES('"
                    + u.getNombre() + "','" + u.getPass().getValor() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int id = rs.getInt(1);
                usuario.setIdUsuario(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar el usuario!", sqle);
        }
        return u;
    }

    @Override
    public Usuario validate(String nombre, String pass) {
        String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        Usuario aux = null;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setString(1, nombre);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }

}
