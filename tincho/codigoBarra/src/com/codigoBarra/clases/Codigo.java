package com.codigoBarra.clases;

public class Codigo {

    private String codigo;

    public Codigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getLongitud() {
        return codigo.length();
    }

}
