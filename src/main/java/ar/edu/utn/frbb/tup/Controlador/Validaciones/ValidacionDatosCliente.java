package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosCliente {
    public void datosCrearCliente(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        if (!datos.containsKey("dni") || !datos.containsKey("nombre") || !datos.containsKey("apellido") || !datos.containsKey("telefono")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        dniValido(datos.get("dni"));
        if (datos.get("nombre").isEmpty() || datos.get("apellido").isEmpty() || datos.get("telefono").isEmpty()) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public Map<String, String> datosModificarCliente(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        if (!datos.containsKey("dni") || (!datos.containsKey("nombre") && !datos.containsKey("apellido") && !datos.containsKey("telefono"))) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        dniValido(datos.get("dni"));
        if (!datos.containsKey("nombre")) {
            datos.put("nombre", "");
        }
        if (!datos.containsKey("apellido")) {
            datos.put("apellido", "");
        }
        if (!datos.containsKey("telefono")) {
            datos.put("telefono", "");
        }

        return datos;
    }

    public void dniValido(String dniString) throws ExcepcionDatosInvalidos{
        try {
            long dni = Long.parseLong(dniString);
            if (dni <= 1000000 || dni >= 999999999) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        } catch (Exception e) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }
}
