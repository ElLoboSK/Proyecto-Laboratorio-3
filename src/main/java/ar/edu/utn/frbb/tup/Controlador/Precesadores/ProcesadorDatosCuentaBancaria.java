package ar.edu.utn.frbb.tup.Controlador.Precesadores;

import java.util.HashMap;
import java.util.Map;

public class ProcesadorDatosCuentaBancaria {
    public static Map<String, String> datosCuentaBancaria(Map<String, String> datosCuentaBancaria) {
        Map<String, String> datos = new HashMap<String, String>();
        
        if (datos.containsKey("dni")) {
            datos.put("dni", datosCuentaBancaria.get("dni"));
        }else{
            datos.put("dni", "");
        }
        if (datos.containsKey("tipoCuenta")) {
            datos.put("tipoCuenta", datosCuentaBancaria.get("tipoCuenta"));
        }else{
            datos.put("tipoCuenta", "");
        }
        if (datos.containsKey("moneda")) {
            datos.put("moneda", datosCuentaBancaria.get("moneda"));
        }else{
            datos.put("moneda", "");
        }

        return datos;
    }
}
