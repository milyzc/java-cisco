package com.javaint.entidades;

import java.io.Serializable;

public class Publicacion implements Serializable {
    private int idApp;
    private int idUsuario;

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
