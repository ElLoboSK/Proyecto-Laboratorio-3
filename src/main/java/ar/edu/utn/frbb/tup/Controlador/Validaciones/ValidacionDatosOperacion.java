package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosOperacion {
    ValidacionDatos validacionEntradas = new ValidacionDatos();

    public void datosOperacionBasica(Map<String, String> datos) throws ExcepcionDatosInvalidos {
        //Se valida que los campos requeridos existan para poder leerlos. Si no existen, se lanza una excepcion.
        if (!datos.containsKey("monto") || !datos.containsKey("idCuentaBancaria")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion doublePositivoValido para validar que el monto ingresado sea un numero real positivo.
        validacionEntradas.doublePositivoValido(datos.get("monto"));
        //Se usa la funcion intPositivoValido para validar que el ID de cuenta ingresado sea un numero entero positivo.
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancaria"));
    }

    public void datosOperacionTransferencia(Map<String, String> datos) throws ExcepcionDatosInvalidos {
        //Se valida que los campos requeridos existan para poder leerlos. Si no existen, se lanza una excepcion.
        if (!datos.containsKey("monto") || !datos.containsKey("idCuentaBancariaOrigen") || !datos.containsKey("idCuentaBancariaDestino")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion doublePositivoValido para validar que el monto ingresado sea un numero real positivo.
        validacionEntradas.doublePositivoValido(datos.get("monto"));
        ///Se usa la funcion intPositivoValido para validar que el ID de cuenta ingresado sea un numero entero positivo.
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancariaOrigen"));
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancariaDestino"));
    }
}
