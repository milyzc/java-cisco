/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.config;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author george
 */
public class Configuracion {
    
    private static final Configuracion INSTANCE = new Configuracion();
    
    private final String connectionString;
    private final String dbUserName, dbPassword;
    
    private Configuracion(){
        try {
            Properties p = new Properties();
            p.load(new FileReader("config.properties"));
            this.connectionString = p.getProperty("url");
            this.dbUserName = p.getProperty("user");
            this.dbPassword = p.getProperty("pass");
        } catch (IOException e) {
            throw new RuntimeException("Error al optener la configuración.");
        }
    }
    
    
    public static Configuracion getInstance(){
        return INSTANCE;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUserName() {
        return dbUserName;
    }
    
    
}
