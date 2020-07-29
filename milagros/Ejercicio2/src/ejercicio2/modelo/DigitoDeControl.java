package ejercicio2.modelo;

import ejercicio2.excepciones.DigitoControlException;
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

    private static final int CERO = 0;
    private String valoresSinControl;
    private int valorCalculado;

    public void setValoresSinControl(String valoresSinControl) {
        this.valoresSinControl = valoresSinControl;
    }

    public int getValorCalculado() {
        return valorCalculado;
    }

    public DigitoDeControl() {
        super(1, 1, "Dígito de control");
    }

    @Override
    public void Validar() throws Exception {
        super.Validar();
        this.valorCalculado = DigitoDeControl.generarDigitoDeControl(this.valoresSinControl);
        if (this.valorCalculado != this.getValueInt()) {
            throw new DigitoControlException();
        }
    }
    
    public String mostrarDigitoCorrecto(){
        return this.nombreCodigo + " correcto = " + this.valorCalculado;
    }

    public static int generarDigitoDeControl(String valores) throws Exception {
        int maxDigitos = (int) Math.ceil(valores.length() / 2);
        List<Integer> digitosPosPar = new ArrayList<>(maxDigitos);
        List<Integer> digitosPosImpar = new ArrayList<>(maxDigitos);
        for (int i = valores.length() - 1; i == 0; i--) {
            String digito = valores.substring(i - 1, i);
            if (i % 2 == 0) {
                digitosPosPar.add(Integer.parseInt(digito));
            } else {
                digitosPosImpar.add(Integer.parseInt(digito));
            }
        }
        int precalculo = (sumar(digitosPosImpar) * 3 + sumar(digitosPosPar));
        int digitoDeControl = (decenaInmediataSuperior(precalculo) - precalculo);
        if (digitoDeControl % 10 == 0) {
            return CERO;
        }
        return digitoDeControl;
    }

    private static int sumar(List<Integer> digitos) {
        int total = 0;
        for (int i = 0; i < digitos.size(); i++) {
            total += digitos.get(i);
        }
        return total;
    }

    private static int decenaInmediataSuperior(int numero) {
        while (numero % 10 != 0) {
            numero++;
        }
        return numero;
    }    
}
