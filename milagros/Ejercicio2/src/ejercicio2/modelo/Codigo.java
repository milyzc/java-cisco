/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2.modelo;

import ejercicio2.excepciones.*;

/**
 *
 * @author TARDIS
 */
public class Codigo {

    protected final int maxLength;
    protected final int minLength;
    protected String valor;
    protected String nombreCodigo;

    public Codigo(int maxLength, int minLength, String nombre) {
        this.maxLength = maxLength;
        this.minLength = minLength;
        this.nombreCodigo = nombre;
    }

    public String getValor() {
        return valor;
    }

    public int getLength() throws Exception {
        if (this.maxLength == this.minLength) {
            return this.maxLength;
        }
        throw new Exception();
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void Validar() throws Exception {
        if (valor.length() < minLength) {
            throw new MinLengthException(minLength,
                    nombreCodigo);
        }
        if (valor.length() > maxLength) {
            throw new MaxLengthException(maxLength, nombreCodigo);
        }
        long digitos = Long.parseLong(valor);
        if (digitos < 0) {
            throw new Exception("Código inválido");
        }
    }

    @Override
    public String toString() {
        return this.nombreCodigo + " = " + this.valor;
    }
    
    public int getValueInt(){
        return Integer.parseInt(valor);
    }
}
