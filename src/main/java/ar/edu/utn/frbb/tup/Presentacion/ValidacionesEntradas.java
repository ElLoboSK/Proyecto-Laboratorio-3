package ar.edu.utn.frbb.tup.Presentacion;

public class ValidacionesEntradas {
    public static boolean intValido(String numeroString) {
        try{
            Integer.parseInt(numeroString);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
