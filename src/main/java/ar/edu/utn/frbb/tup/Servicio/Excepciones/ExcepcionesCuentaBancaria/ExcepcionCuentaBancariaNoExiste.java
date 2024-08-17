package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria;

public class ExcepcionCuentaBancariaNoExiste extends Exception {
    public ExcepcionCuentaBancariaNoExiste(String message) {
        super(message);
    }
}
