package com.codigoBarra;

import com.codigoBarra.clases.CodigoBarra;

import java.util.Scanner;

public class Ejecutable {
//  Ej:
//    Ingrese un código de país.(3)
//            779
//    Ingrese un código de Empresa.(4/5)
//            4000
//    Ingrese un código de Producto.(4/5)
//            96009
    public static void main(String[] args) {
        String menu = "s";
        Scanner input = new Scanner(System.in);
        while (menu.equals("s")) {
            System.out.println("Ingrese un código de país.(3)");
            String codigoPais = input.next();
            System.out.println("Ingrese un código de Empresa.(4/5)");
            String codigoEmpresa = input.next();
            System.out.println("Ingrese un código de Producto.(4/5)");
            String codigoProducto = input.next();
            try {
                CodigoBarra codigoBarra = new CodigoBarra(codigoPais, codigoEmpresa, codigoProducto);
                System.out.println(codigoBarra.esValido());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Ingrese 's' para resetear.");
            menu = input.next();
        }
    }
}
