package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosPrestamo {
    ValidacionDatosCliente validacionDatosCliente = new ValidacionDatosCliente();
    ValidacionDatos validacionEntradas = new ValidacionDatos();
    ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria = new ValidacionDatosCuentaBancaria();
    
    public void datosPrestamo(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        //Se valida que los campos requeridos existan para poder leerlos. Si no existen, se lanza una excepcion.
        if (!datos.containsKey("numeroCliente") || !datos.containsKey("plazoMeses") || !datos.containsKey("montoPrestamo") || !datos.containsKey("moneda")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion dniValido para validar el DNI ingresado.
        validacionDatosCliente.dniValido(datos.get("numeroCliente"));
        //Se usa la funcion intPositivoValido para validar que el plazo ingresado sea un numero entero positivo.
        validacionEntradas.intPositivoValido(datos.get("plazoMeses"));
        //Se usa la funcion doublePositivoValido para validar que el monto ingresado sea un numero decimal positivo.
        validacionEntradas.doublePositivoValido(datos.get("montoPrestamo"));
        //Se usa la funcion monedaValido para validar que la moneda ingresada este dentro de las monedas permitidas.
        validacionDatosCuentaBancaria.monedaValido(datos.get("moneda"));
    }
}
