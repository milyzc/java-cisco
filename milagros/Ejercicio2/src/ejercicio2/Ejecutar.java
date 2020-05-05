/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2;

import ejercicio2.modelo.CodigoDeBarra;
import java.util.Scanner;

/**
 *
 * @author TARDIS
 */
public class Ejecutar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String codigoIngresado;
        System.out.println("Ingrese un código de barras: ");
        codigoIngresado = sc.next();
        CodigoDeBarra cb = new CodigoDeBarra(codigoIngresado);
        try {
            cb.Validar();
            cb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
