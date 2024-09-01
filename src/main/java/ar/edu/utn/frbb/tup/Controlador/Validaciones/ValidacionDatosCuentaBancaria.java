package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosCuentaBancaria {
    ValidacionDatosCliente validacionDatosCliente = new ValidacionDatosCliente();

    public void datosCrearCuentaBancaria(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        //Se valida que los campos requeridos existan para poder leerlos. Si no existen, se lanza una excepcion.
        if (!datos.containsKey("dni") || !datos.containsKey("tipoCuenta") || !datos.containsKey("moneda")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        //Se usa la funcion dniValido para validar el DNI ingresado.
        validacionDatosCliente.dniValido(datos.get("dni"));
        //Se usa la funcion tipoCuentaValido para validar que el tipo de cuenta ingresado este dentro de los tipos de cuenta permitidos.
        tipoCuentaValido(datos.get("tipoCuenta"));
        //Se usa la funcion monedaValido para validar que la moneda ingresada este dentro de las monedas permitidas.
        monedaValido(datos.get("moneda"));
    }

    public void tipoCuentaValido(String tipoCuenta) throws ExcepcionDatosInvalidos {
        //Se valida que el tipo de cuenta ingresado sea uno de los permitidos. Si no lo es, se lanza una excepcion.
        if (!tipoCuenta.equals("cuenta corriente") && !tipoCuenta.equals("caja de ahorro")) {
            throw new ExcepcionDatosInvalidos("El tipo de cuenta ingresado es invalido");
        }
    }

    public void monedaValido(String moneda) throws ExcepcionDatosInvalidos {
        //Se valida que la moneda ingresada sea una de las permitidas. Si no lo es, se lanza una excepcion.
        if (!moneda.equals("pesos") && !moneda.equals("dolares")) {
            throw new ExcepcionDatosInvalidos("La moneda ingresada es invalida");
        }
    }
}
