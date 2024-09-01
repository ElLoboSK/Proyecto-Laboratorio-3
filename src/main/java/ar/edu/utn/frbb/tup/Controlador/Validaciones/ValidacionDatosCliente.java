package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosCliente {
    public void datosCrearCliente(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        //Se valida que los campos requeridos existan para poder leerlos. Si no existen, se lanza una excepcion.
        if (!datos.containsKey("dni") || !datos.containsKey("nombre") || !datos.containsKey("apellido") || !datos.containsKey("telefono")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion dniValido para validar el DNI ingresado.
        dniValido(datos.get("dni"));
        //Se valida que los campos no esten vacios. Si estan vacios, se lanza una excepcion.
        if (datos.get("nombre").isEmpty() || datos.get("apellido").isEmpty() || datos.get("telefono").isEmpty()) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public Map<String, String> datosModificarCliente(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        //Se valida que los campos requeridos existan para poder leerlos. En caso de que no existe el campo DNI o que no existan ninguno de los otros 3 campos, se lanza una excepcion.
        if (!datos.containsKey("dni") || (!datos.containsKey("nombre") && !datos.containsKey("apellido") && !datos.containsKey("telefono"))) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion dniValido para validar el DNI ingresado.
        dniValido(datos.get("dni"));
        //Se observa cual de los 3 campos esta vacio y cual no para unicamente devolver los datos que no estan vacios. Esto es para que que no sea obligatorio llenar todos los campos, si solo se quiere cambiar uno.
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
        //Se valida que el DNI ingresado sea un numero entero. Si no lo es, se lanza una excepcion.
        try {
            long dni = Long.parseLong(dniString);
            //Ademas se valida que el DNI tenga entre 7 y 9 digitos. Si esta fuera de ese rango, se lanza una excepcion.
            if (dni <= 1000000 || dni >= 999999999) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        } catch (Exception e) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }
}
