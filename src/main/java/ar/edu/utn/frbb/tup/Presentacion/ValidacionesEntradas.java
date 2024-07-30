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

    public static boolean doubleValido(String numeroString) {
        try{
            Double.parseDouble(numeroString);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
