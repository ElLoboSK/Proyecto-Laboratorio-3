package ar.edu.utn.frbb.tup.Servicio.Validaciones;

public class ValidacionesCuentaBancaria {
    public static boolean cbuValido(String cbuString) {
        try{
            long cbu=Long.parseLong(cbuString);
            if (cbu > 100000 && cbu < 999999) {
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean tipoCuentaValido(String tipoCuenta) {
        if (tipoCuenta!=null) {
            if (tipoCuenta.equals("Caja de ahorro") || tipoCuenta.equals("Cuenta corriente")) {
                return true;
            }
        }
        return false;
    }

    public static boolean monedaValido(String moneda) {
        if (moneda!=null) {
            if (moneda.equals("Dolares") || moneda.equals("Pesos")) {
                return true;
            }
        }
        return false;
    }
}
    