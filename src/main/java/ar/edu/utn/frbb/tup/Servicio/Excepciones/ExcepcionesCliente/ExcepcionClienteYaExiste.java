package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente;

public class ExcepcionClienteYaExiste extends Exception{
    public ExcepcionClienteYaExiste(String message) {
        super(message);
    }
}
