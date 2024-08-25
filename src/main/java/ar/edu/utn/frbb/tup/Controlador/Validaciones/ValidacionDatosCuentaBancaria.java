package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosCuentaBancaria {
    ValidacionDatosCliente validacionDatosCliente = new ValidacionDatosCliente();

    public void datosCrearCuentaBancaria(Map<String, String> datos) throws ExcepcionDatosInvalidos{
        if (!datos.containsKey("dni") || !datos.containsKey("tipoCuenta") || !datos.containsKey("moneda")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        validacionDatosCliente.dniValido(datos.get("dni"));
        tipoCuentaValido(datos.get("tipoCuenta"));
        monedaValido(datos.get("moneda"));
    }

    public void tipoCuentaValido(String tipoCuenta) throws ExcepcionDatosInvalidos {
        if (!tipoCuenta.equals("cuenta corriente") && !tipoCuenta.equals("caja de ahorro")) {
            throw new ExcepcionDatosInvalidos("El tipo de cuenta ingresado es invalido");
        }
    }

    public void monedaValido(String moneda) throws ExcepcionDatosInvalidos {
        if (!moneda.equals("pesos") && !moneda.equals("dolares")) {
            throw new ExcepcionDatosInvalidos("La moneda ingresada es invalida");
        }
    }
}
