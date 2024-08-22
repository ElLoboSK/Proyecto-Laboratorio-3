package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.HashMap;
import java.util.Map;

public class ValidacionDatosCliente {
    public static Map<String, String> datosCliente(Map<String, String> datosCliente) {
        Map<String, String> datos = new HashMap<String, String>();

        if (datos.containsKey("dni")) {
            datos.put("dni", datosCliente.get("dni"));
        }else{
            datos.put("dni", "");
        }
        if (datos.containsKey("nombre")) {
            datos.put("nombre", datosCliente.get("nombre"));
        }else{
            datos.put("nombre", "");
        }
        if (datos.containsKey("apellido")) {
            datos.put("apellido", datosCliente.get("apellido"));
        }else{
            datos.put("apellido", "");
        }
        if (datos.containsKey("telefono")) {
            datos.put("telefono", datosCliente.get("telefono"));
        }else{
            datos.put("telefono", "");
        }

        return datos;
    }
}
