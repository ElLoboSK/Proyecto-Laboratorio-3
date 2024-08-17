package ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria;

public class ExcepcionCuentaBancariaYaExiste extends Exception{
    public ExcepcionCuentaBancariaYaExiste(String mensaje){
        super(mensaje);
    }
}
