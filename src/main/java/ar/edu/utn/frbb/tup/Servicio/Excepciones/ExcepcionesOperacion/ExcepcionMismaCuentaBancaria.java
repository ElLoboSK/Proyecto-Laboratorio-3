package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion;

public class ExcepcionMismaCuentaBancaria extends Exception {
    public ExcepcionMismaCuentaBancaria(String mensaje) {
        super(mensaje);
    }
}
