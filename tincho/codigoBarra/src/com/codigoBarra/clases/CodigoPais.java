package com.codigoBarra.clases;

public class CodigoPais extends Codigo{

    private static int longitud = 3;
    
    public CodigoPais(String codigo) throws Exception {
        super(codigo);
        ValidarLongitud();
    }

    public void ValidarLongitud() throws Exception {
        if(super.getCodigo().length() != longitud)
        {
            throw new Exception("El código de país ingresado no tiene la longitud necesaria.");
        }
    }
}
