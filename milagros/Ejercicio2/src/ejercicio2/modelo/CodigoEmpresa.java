/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2.modelo;

import java.util.Random;

/**
 *
 * @author TARDIS
 */
public class CodigoEmpresa extends Codigo {

    public CodigoEmpresa() {
        super( 4,  5, "Codigo de empresa");
    }

    @Override
    public int getLength() {
        try {
            return super.getLength();
        } catch (Exception e) {
            Random r = new Random();
            return  (r.nextInt(this.maxLength - this.minLength) + this.minLength);
        }
    }
}
