package ar.edu.utn.frbb.tup.Servicio.Validaciones;

import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;

public class ValidacionesOperacion {
    public static boolean montoValido(String montoString){
        if (ValidacionesEntradas.doubleValido(montoString) && Double.parseDouble(montoString)>=0) {
            return true;
        }else{
            return false;
        }
    }
}
