package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria;

public class ExcepcionCuentaBancariaMonedaNoExiste extends Exception {
    public ExcepcionCuentaBancariaMonedaNoExiste(String message) {
        super(message);
    }
}
