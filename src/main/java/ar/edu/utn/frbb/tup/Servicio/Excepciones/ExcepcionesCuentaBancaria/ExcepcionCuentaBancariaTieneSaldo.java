package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria;

public class ExcepcionCuentaBancariaTieneSaldo extends Exception{
    public ExcepcionCuentaBancariaTieneSaldo(String mensaje) {
        super(mensaje);
    }
}
