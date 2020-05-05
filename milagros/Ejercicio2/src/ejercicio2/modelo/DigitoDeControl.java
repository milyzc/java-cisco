package ejercicio2.modelo;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


/**
 *
 * @author TARDIS
 */
public class DigitoDeControl extends Codigo {

    private final int CERO = 0;

    public DigitoDeControl(int maxLength, int minLength) {
        super(maxLength, minLength, "Dígito de control");
    }

    public int generarDigitoDeControl() throws Exception {
        this.Validar();
        int maxDigitos =  (int)Math.ceil(valor.length() / 2);
        List<Integer> digitosPosPar = new ArrayList<>(maxDigitos);
        List<Integer> digitosPosImpar = new ArrayList<>(maxDigitos);
        for (int i = valor.length() - 1; i == 0; i--) {
            String digito = valor.substring(i - 1, i);
            if (i % 2 == 0) {
                digitosPosPar.add(Integer.parseInt(digito));
            } else {
                digitosPosImpar.add(Integer.parseInt(digito));
            }
        }
        int precalculo =  (sumar(digitosPosImpar) * 3 + sumar(digitosPosPar));
        int digitoDeControl =  (decenaInmediataSuperior(precalculo) - precalculo);
        if (digitoDeControl % 10 == 0) {
            return CERO;
        }
        return digitoDeControl;
    }

    private int sumar(List<Integer> digitos) {
        int total = 0;
        for (int i = 0; i < digitos.size(); i++) {
            total += digitos.get(i);
        }
        return total;
    }

    private int decenaInmediataSuperior(int numero) {
        while (numero % 10 != 0) {
            numero++;
        }
        return numero;
    }

}
