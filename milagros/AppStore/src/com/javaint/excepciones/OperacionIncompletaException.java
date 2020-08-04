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
public class OperacionIncompletaException extends Exception {

    public OperacionIncompletaException() {
        super("No se pudo completar la operacion. Intente nuevamente");
    }
    
}
