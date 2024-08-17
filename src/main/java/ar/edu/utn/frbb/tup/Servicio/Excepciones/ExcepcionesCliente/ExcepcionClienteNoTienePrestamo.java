package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente;

public class ExcepcionClienteNoTienePrestamo extends Exception {
    public ExcepcionClienteNoTienePrestamo(String message) {
        super(message);
    }
}
