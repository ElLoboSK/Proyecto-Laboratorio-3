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
        if (!datos.containsKey("numeroCliente") || !datos.containsKey("plazoMeses") || !datos.containsKey("montoPrestamo") || !datos.containsKey("moneda")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        validacionDatosCliente.dniValido(datos.get("numeroCliente"));
        validacionEntradas.intPositivoValido(datos.get("plazoMeses"));
        validacionEntradas.doublePositivoValido(datos.get("montoPrestamo"));
        validacionDatosCuentaBancaria.monedaValido(datos.get("moneda"));
    }
}
