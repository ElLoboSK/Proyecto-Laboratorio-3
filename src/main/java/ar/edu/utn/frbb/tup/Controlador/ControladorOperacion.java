package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosOperacion;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;

import java.util.Map;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/operacion")
public class ControladorOperacion {
    private ServicioOperacion servicioOperacion;
    private ValidacionDatosOperacion validacionDatosOperacion;

    public ControladorOperacion(ServicioOperacion servicioOperacion, ValidacionDatosOperacion validacionDatosOperacion){
        this.servicioOperacion=servicioOperacion;
        this.validacionDatosOperacion=validacionDatosOperacion;
    }

    @PostMapping("/depositar")
    public ResponseEntity<Movimiento> depositar(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos {
        validacionDatosOperacion.datosOperacionBasica(datos);
        return new ResponseEntity<>(servicioOperacion.depositar(datos.get("monto"), datos.get("idCuentaBancaria")), HttpStatus.CREATED);
    }
    
    @PostMapping("/retirar")
    public ResponseEntity<Movimiento> retirar(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente {
        validacionDatosOperacion.datosOperacionBasica(datos);
        return new ResponseEntity<>(servicioOperacion.retirar(datos.get("monto"), datos.get("idCuentaBancaria")), HttpStatus.CREATED);
    }

    @PostMapping("/transferir")
    public ResponseEntity<List<Movimiento>> transferir(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria {
        validacionDatosOperacion.datosOperacionTransferencia(datos);
        return new ResponseEntity<>(servicioOperacion.transferir(datos.get("monto"), datos.get("idCuentaBancariaOrigen"), datos.get("idCuentaBancariaDestino")), HttpStatus.CREATED);
    }
}
