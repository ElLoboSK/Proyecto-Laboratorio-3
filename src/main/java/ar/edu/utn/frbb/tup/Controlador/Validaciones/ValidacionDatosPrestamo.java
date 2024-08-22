package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.HashMap;
import java.util.Map;

public class ValidacionDatosPrestamo {
    public static Map<String, String> datosPrestamo(Map<String, String> datosPrestamo){
        Map<String, String> datos = new HashMap<>();

        if (datosPrestamo.containsKey("numeroCliente")) {
            datos.put("numeroCliente", datosPrestamo.get("numeroCliente"));
        }else{
            datos.put("numeroCliente", "");
        }
        if (datosPrestamo.containsKey("plazoMeses")) {
            datos.put("plazoMeses", datosPrestamo.get("plazoMeses"));
        }else{
            datos.put("plazoMeses", "");
        }
        if (datosPrestamo.containsKey("montoPrestamo")) {
            datos.put("montoPrestamo", datosPrestamo.get("montoPrestamo"));
        }else{
            datos.put("montoPrestamo", "");
        }
        if (datosPrestamo.containsKey("moneda")) {
            datos.put("moneda", datosPrestamo.get("moneda"));
        }else{
            datos.put("moneda", "");
        }

        return datos;
    }
}
