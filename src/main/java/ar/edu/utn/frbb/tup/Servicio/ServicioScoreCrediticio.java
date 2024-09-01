package ar.edu.utn.frbb.tup.Servicio;

import org.springframework.stereotype.Component;

@Component
public class ServicioScoreCrediticio {
    public boolean scoreCrediticio(long dni){
        //Se verifica si el DNI ingresado es par o impar. Si es par, se devuelve true. Si es impar, se devuelve false.
        if (dni%2==0) {
            return true;
        }else{
            return false;
        }
    }
}