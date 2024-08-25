package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatosOperacion {
    ValidacionEntradas  validacionEntradas = new ValidacionEntradas();

    public void datosOperacionBasica(Map<String, String> datos) throws ExcepcionDatosInvalidos {
        if (!datos.containsKey("monto") || !datos.containsKey("idCuentaBancaria")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        validacionEntradas.doublePositivoValido(datos.get("monto"));
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancaria"));
    }

    public void datosOperacionTransferir(Map<String, String> datos) throws ExcepcionDatosInvalidos {
        if (!datos.containsKey("monto") || !datos.containsKey("idCuentaBancariaOrigen") || !datos.containsKey("idCuentaBancariaDestino")) {
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }

        validacionEntradas.doublePositivoValido(datos.get("monto"));
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancariaOrigen"));
        validacionEntradas.intPositivoValido(datos.get("idCuentaBancariaDestino"));
    }
}
