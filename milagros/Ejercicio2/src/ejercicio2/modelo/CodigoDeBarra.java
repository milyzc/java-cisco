/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2.modelo;

/**
 *
 * @author TARDIS
 */
public class CodigoDeBarra extends Codigo {

    private CodigoPais codigoDePais;
    private CodigoEmpresa codigoDeEmpresa;
    private CodigoProducto codigoDeProducto;
    private DigitoDeControl digito;

    public CodigoDeBarra(String valor) {
        super(13, 13, "Codigo de barras");
        this.valor = valor;
    }

    @Override
    public String toString() {
        String aux = super.toString();
        return aux + ":"
                + System.lineSeparator()
                + codigoDePais.toString()
                + System.lineSeparator()
                + codigoDeEmpresa.toString()
                + System.lineSeparator()
                + codigoDeProducto.toString()
                + System.lineSeparator()
                + digito.toString();
    }

    @Override
    public void Validar() throws Exception {
        super.Validar();
        this.inicializarCodigos();
        this.setCodigos();
        codigoDePais.Validar();
        codigoDeEmpresa.Validar();
        codigoDeProducto.Validar();
        digito.Validar();        
    }

    private void setCodigos() throws Exception {
        
        int indexFinSinControl = this.valor.length() - 1;
        int indexInicio = 0;
        this.digito.setValor(this.valor.substring(indexFinSinControl));
        this.digito.setValoresSinControl(
                this.valor.substring(indexInicio, indexFinSinControl));
        int indexFin = this.codigoDePais.getLength();
        this.codigoDePais.setValor(this.valor.substring(indexInicio, indexFin));
        indexInicio = indexFin;
        indexFin = indexInicio + this.codigoDeEmpresa.getLength();
        this.codigoDeEmpresa.setValor(this.valor.substring(indexInicio, indexFin));
        indexInicio = indexFin;
        this.codigoDeProducto.setValor(this.valor.substring(indexInicio, indexFinSinControl));
    }

    private void inicializarCodigos() {
        this.codigoDePais = new CodigoPais();
        this.codigoDeEmpresa = new CodigoEmpresa();
        this.codigoDeProducto = new CodigoProducto();
        this.digito = new DigitoDeControl();
    }
    
    public String mostrarDigitoCorrecto(){
        return this.digito.mostrarDigitoCorrecto();
    }
}
