package ar.edu.utn.frbb.tup.Controlador.Precesadores;

import java.util.HashMap;
import java.util.Map;

public class ProcesadorDatosOperacion {
    public static Map<String, String> datosOperacion(Map<String, String> datosOperacion) {
        Map<String, String> datos = new HashMap<>();

        if (datosOperacion.containsKey("monto")) {
            datos.put("monto", datosOperacion.get("monto"));
        }else{
            datos.put("monto", "");
        }
        if (datosOperacion.containsKey("idCuentaBancaria")) {
            datos.put("idCuentaBancaria", datosOperacion.get("idCuentaBancaria"));
        }else{
            datos.put("idCuentaBancaria", "");
        }
        if (datosOperacion.containsKey("idCuentaBancariaOrigen")) {
            datos.put("idCuentaBancariaOrigen", datosOperacion.get("idCuentaBancariaOrigen"));
        }else{
            datos.put("idCuentaBancariaOrigen", "");
        }
        if (datosOperacion.containsKey("idCuentaBancariaDestino")) {
            datos.put("idCuentaBancariaDestino", datosOperacion.get("idCuentaBancariaDestino"));
        }else{
            datos.put("idCuentaBancariaDestino", "");
        }

        return datos;
    }
}
