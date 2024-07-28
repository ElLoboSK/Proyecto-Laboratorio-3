package ar.edu.utn.frbb.tup.Servicio.Validaciones;

public class ValidacionesCliente {
    public static boolean dniValido(String dniString) {
        try {
            long dni = Long.parseLong(dniString);
            if (dni > 1000000 && dni < 999999999) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
