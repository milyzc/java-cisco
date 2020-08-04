/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.servicios;

import com.javaint.dao.*;
import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Password;
import com.javaint.entidades.Persona;
import com.javaint.entidades.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author MARTIN Una clase de servicios que nos conecte con los datos y no
 * resuelva la lógica de negocio.
 */
public class GestorUsuarios {

    private final UsuarioDao usuarioDao;
    private final PersonaDao personaDao;

    private Persona userLog;

    public Persona getUserLog() {
        return userLog;
    }

    public GestorUsuarios() {
        usuarioDao = new UsuarioDaoJDBC();
        personaDao = new PersonaDaoJDBC();
    }

    public boolean login(String user, String pass) {
        boolean loginOK;
        userLog = personaDao.validate(user, pass);

        loginOK = userLog != null;
        try {
            checkFirstLogin(user);
        } catch (IOException ioe) {
            throw new RuntimeException("Problema de login!", ioe);
        }
        return loginOK;
    }


    public boolean registrarUsuario(String apellidoNombre, String dni,
                                    String email,String usuario, String password){
        Persona persona = new Persona(apellidoNombre, dni, email, usuario, password);
        return this.personaDao.crear(persona);
    }

    /**
     * Chequea si existe su directorio de trabajo "workspace/userName". Si no
     * existe, es el primer login y lo crea, copiando la imagen por defecto al
     * directorio correspondiente.
     *
     * @param userName
     */
    private void checkFirstLogin(String userName) throws IOException {
        Path workspaceDir = Paths.get("workspace", userName);

        // Se comprueba si existe o no
        if (!Files.exists(workspaceDir)) {
            //Si no existe el directorio, hay que crearlo
            Files.createDirectories(workspaceDir);

        }

        Path imageFile = Paths.get(getUserAvatar());
        if (!Files.exists(imageFile)) {
            // Se arma el path por defecto
            Path defaultFile = Paths.get("images", "default.png");
            // Se copia la imagen
            Files.copy(defaultFile, imageFile);
        }
    }

    public String getUserAvatar() {
        if (userLog == null) {
            throw new RuntimeException("No hay usuario logueado");
        }

        return Paths.get("workspace", this.userLog.getUsuario().getNombre(), "avatar.png").
                toAbsolutePath().toString();
    }

    public boolean existeMail(String email) {
        return personaDao.existeEmail(email);
    }

    public boolean editarUsuario(int idUsuario,String apellidoNombre, String dni,
                                 String email,String usuario, String password) {
        Persona persona = this.personaDao.obtenerPersonaXidUsuario(idUsuario);
        persona.setApellidoNombre(apellidoNombre);
        persona.setDni(dni);
        persona.setEmail(email);
        persona.getUsuario().setNombre(usuario);
        persona.getUsuario().setPass(new Password(password));
        return this.personaDao.editar(persona);
    }
}
