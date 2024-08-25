package ar.edu.utn.frbb.tup.Servicio;

import org.springframework.stereotype.Component;

@Component
public class ServicioScoreCrediticio {
    public boolean scoreCrediticio(long dni){
        if (dni%2==0) {
            return true;
        }else{
            return false;
        }
    }
}