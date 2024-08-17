package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria;

public class ExcepcionNoHayCuentasBancarias extends Exception {
    public ExcepcionNoHayCuentasBancarias(String message) {
        super(message);
    }
}
