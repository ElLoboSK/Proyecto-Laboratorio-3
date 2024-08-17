package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente;

public class ExcepcionClienteTienePrestamo extends Exception{
    public ExcepcionClienteTienePrestamo(String message) {
        super(message);
    }
}
