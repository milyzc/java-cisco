package com.javaint.dao;

import com.javaint.config.Configuracion;
import com.javaint.entidades.Persona;
import com.javaint.entidades.Usuario;

import java.sql.*;

public class PersonaDaoJDBC implements PersonaDao {

    private final Configuracion config;
    private final UsuarioDao usuarioDao;

    public PersonaDaoJDBC() {
        this.config = Configuracion.getInstance();
        this.usuarioDao = new UsuarioDaoJDBC();
    }

    @Override
    public boolean crear(Persona p) {
        int rows;
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(),
                config.getDbPassword());
             Statement stm = cnn.createStatement()) {
            cnn.setAutoCommit(false);
            Usuario usuario = usuarioDao.create(p.getUsuario());
            if (usuario != null) {
                rows = stm.executeUpdate("INSERT INTO personas (apellido_nombre," +
                        " dni, email, id_usuario) VALUES('"
                        + p.getApellidoNombre() + "','" +
                        p.getDni() + "','" +
                        p.getEmail() + "','" +
                        usuario.getIdUsuario() + "')");
                cnn.commit();
            } else
                return false;

        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo insertar la persona!", sqle);
        }
        return rows == 1;
    }

    @Override
    public boolean existeEmail(String email) {
        String query = "SELECT * FROM personas WHERE email = ? ";
        boolean aux = false;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setString(1, email);
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
    public Persona obtenerPersonaXidUsuario(int idUsuario) {
        String query = "SELECT * FROM personas WHERE id_usuario = ? ";
        Persona aux = null;
        Configuracion config = Configuracion.getInstance();
        try (Connection cnn = DriverManager.getConnection(config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = new Persona(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        usuarioDao.obtenerXidUsuario(rs.getInt(4))
                );
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD!", sqle);
        }
        return aux;
    }

    @Override
    public boolean editar(Persona persona) {
        int rows;
        String query = "UPDATE personas SET apellido_nombre=?,dni=?,email=? where id_persona=?";
        try (Connection cnn = DriverManager.getConnection(
                config.getConnectionString(), config.getDbUserName(), config.getDbPassword());
             PreparedStatement stm = cnn.prepareStatement(query)) {
            boolean usuario = usuarioDao.editar(persona.getUsuario());
            if (usuario) {
                stm.setString(1, persona.getApellidoNombre());
                stm.setString(2, persona.getDni());
                stm.setString(3, persona.getEmail());
                stm.setInt(4, persona.getIdPersona());
                rows = stm.executeUpdate();
            } else
                return false;
        } catch (SQLException sqle) {
            throw new RuntimeException("Error de BD. No se pudo editar la persona!", sqle);
        }
        return rows == 1;
    }

    @Override
    public Persona validate(String user, String pass) {
        Usuario usuario = usuarioDao.validate(user, pass);
        if (usuario != null) {
            return obtenerPersonaXidUsuario(usuario.getIdUsuario());
        }
        return null;
    }
}
