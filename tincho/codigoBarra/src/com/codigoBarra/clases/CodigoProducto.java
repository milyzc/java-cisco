package com.codigoBarra.clases;

public class CodigoProducto extends Codigo {

    private static int longitudDesde = 4;
    private static int longitudHasta = 5;

    public CodigoProducto(String codigo) throws Exception {
        super(codigo);
        ValidarLongitud();
    }

    public void ValidarLongitud() throws Exception {
        if(!(super.getCodigo().length() <= longitudDesde &&
                super.getCodigo().length() >= longitudHasta))
        {
            throw new Exception("El código de producto ingresado no tiene la longitud necesaria.");
        }
    }

}
