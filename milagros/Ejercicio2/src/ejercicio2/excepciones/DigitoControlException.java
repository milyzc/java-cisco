/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2.excepciones;

/**
 *
 * @author TARDIS
 */
public class DigitoControlException extends Exception{

    public DigitoControlException() {
        super("Dígito de control inválido");
    }
    
}
