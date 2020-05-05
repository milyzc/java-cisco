package com.codigoBarra.clases;

public class CodigoBarra {

    private CodigoPais codigoPais;
    private CodigoEmpresa codigoEmpresa;
    private CodigoProducto codigoProducto;
    private final int Long_EAN13 = 12;

    public CodigoBarra(String codigoPais, String codigoEmpresa, String codigoProducto) throws Exception {
        this.codigoPais = new CodigoPais(codigoPais);
        this.codigoEmpresa = new CodigoEmpresa(codigoEmpresa);
        this.codigoProducto = new CodigoProducto(codigoProducto);
        ValidarLongitud();
    }

    private int controlCodeCalculator(String firstTwelveDigits) {
        char[] charDigits = firstTwelveDigits.toCharArray();
        int[] ean13 = {1, 3};
        int sum = 0;
        for (int i = 0; i < charDigits.length; i++) {
            sum += Character.getNumericValue(charDigits[i]) * ean13[i % 2];
        }
        int checksum = 10 - sum % 10;

        if (checksum == 10)
            checksum = 0;

        return checksum;
    }

    private String codigoString() {
        return codigoPais.toString() + codigoEmpresa.toString() + codigoProducto.toString();
    }


    public String esValido() {
        return controlCodeCalculator(codigoString()) == 0 ?
                ("Codigo de Barra: " +
                        "Pais =" + codigoPais.getCodigo() +
                        ", Empresa =" + codigoEmpresa.getCodigo() +
                        ", Producto =" + codigoProducto.getCodigo()) : "Código de barra invalido.";
    }

    public void ValidarLongitud() throws Exception {
        Integer cont = codigoPais.getLongitud() + codigoEmpresa.getLongitud() + codigoProducto.getLongitud();
        if (cont != Long_EAN13) {
            throw new Exception("El código de barra ingresado no tiene la longitud adecuada.");
        }
    }
}
