package com.codigoBarra.clases;

public class CodigoEmpresa extends Codigo {

    private static int longitudDesde = 4;
    private static int longitudHasta = 5;

    public CodigoEmpresa(String codigo) throws Exception {
        super(codigo);
        ValidarLongitud();
    }

    public void ValidarLongitud() throws Exception {
        if (!(super.getCodigo().length() <= longitudDesde &&
                super.getCodigo().length() >= longitudHasta)) {
            throw new Exception("El código de empresa ingresado no tiene la longitud necesaria.");
        }
    }
}
