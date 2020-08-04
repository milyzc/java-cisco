/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Rol;
import com.javaint.entidades.Usuario;
import com.javaint.excepciones.OperacionIncompletaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 *
 * @author MARTIN
 */
public class UsuarioDaoJDBC implements UsuarioDao {

    private final Configuracion config;
    private final PersonaDao personaDao;

    public UsuarioDaoJDBC() {
        this.config = Configuracion.getInstance();
        this.personaDao = new PersonaDaoJDBC();
    }

    @Override
    public boolean create(Usuario u) {
        Connection cnn;
        int rows = 0;
        String queryPersona = "INSERT INTO personas (nombre_apellido, email, dni) VALUES";
        queryPersona += "(?,?,?)";
        String queryUsuario = "INSERT INTO usuarios (nombre_usuario, password, id_rol, id_persona) VALUES";
        queryUsuario += "(?,?,?,?)";
        try {
            cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar el usuario!", sqle);
        }
        try (PreparedStatement psPersona = cnn.prepareStatement(queryPersona);
                PreparedStatement psUsuario = cnn.prepareStatement(queryUsuario);) {
            cnn.setAutoCommit(false);
            psPersona.setString(1, u.getPersona().getNombreApellido());
            psPersona.setString(2, u.getPersona().getEmail());
            psPersona.setString(3, u.getPersona().getDni());
            rows = psPersona.executeUpdate();
            if (rows == 0) {
                return false;
            }
            ResultSet rs = psPersona.getGeneratedKeys();
            if (rs.next()) {
                u.getPersona().setId(rs.getInt(1));
            }
            psUsuario.setString(1, u.getNombre());
            psUsuario.setString(2, u.getPass().getValor());
            psUsuario.setString(3, Rol.CONSUMIDOR.toString());
            psUsuario.setString(4, u.getPersona().getId().toString());
            rows = psUsuario.executeUpdate();
            cnn.commit();
        } catch (SQLIntegrityConstraintViolationException sic) {
            try {
                cnn.rollback();
                throw new RuntimeException("nombre de usuario/email ya registrado");
            } catch (SQLException e) {                
            }
        } catch (SQLException sqle) {
            try {
                cnn.rollback();
                throw new RuntimeException("Error de BD. No se pudo insertar el usuario!", sqle);
            } catch (SQLException e) {                
            }
        } finally {
            try {
                cnn.close();
            } catch (SQLException e) {
            }
        }
        return rows == 1;

    }

    @Override
    public Usuario validate(String nombre, String pass) {
        String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND password = ?";
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

    @Override
    public void editar(Usuario usr) throws Exception {
        int rows = 0;
        String queryUsuario = "UPDATE usuarios SET nombre_usuario=?, password=?  WHERE id_usuario=?";
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
                PreparedStatement psUsuario = cnn.prepareStatement(queryUsuario);) {
            psUsuario.setString(1, usr.getNombre());
            psUsuario.setString(2, usr.getPass().getValor());
            psUsuario.setString(3, usr.getId().toString());
            rows = psUsuario.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sic) {
            throw new RuntimeException("nombre de usuario/email ya registrado");
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar el usuario!", sqle);
        }
        if (rows == 0) {
            throw new OperacionIncompletaException();
        }
    }

}
