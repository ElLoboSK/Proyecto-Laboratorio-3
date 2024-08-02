package ar.edu.utn.frbb.tup.Servicio;

public class ServicioScoreCrediticio {
    public static boolean scoreCrediticio(long dni){
        if (dni%2==0) {
            return true;
        }else{
            return false;
        }
    }
}