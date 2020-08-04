/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.excepciones;

/**
 *
 * @author mily_
 */
public class RequeridoException extends Exception {

    public RequeridoException(String campo) {
        super("El campo "+ campo + " es requerido");
    }
    
}
