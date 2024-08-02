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

    public static boolean intPositivoValido(String numeroString) {
        try{
            int numero=Integer.parseInt(numeroString);
            if (numero>=0) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean doublePositivoValido(String numeroString) {
        try{
            double numero=Double.parseDouble(numeroString);
            if (numero>=0) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
