package ar.edu.utn.frbb.tup.Controlador;

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
    
    @PostMapping("/depositar")
    public ResponseEntity<Movimiento> depositar(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos {
        String monto="";
        String idCuentaBancaria="";
        if(datos.containsKey("monto")) {
            monto=datos.get("monto");
        }
        if(datos.containsKey("idCuentaBancaria")) {
            idCuentaBancaria=datos.get("idCuentaBancaria");
        }
        return new ResponseEntity<>(ServicioOperacion.depositar(monto, idCuentaBancaria), HttpStatus.CREATED);
    }
    
    @PostMapping("/retirar")
    public ResponseEntity<Movimiento> retirar(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente {
        String monto="";
        String idCuentaBancaria="";
        if(datos.containsKey("monto")) {
            monto=datos.get("monto");
        }
        if(datos.containsKey("idCuentaBancaria")) {
            idCuentaBancaria=datos.get("idCuentaBancaria");
        }
        return new ResponseEntity<>(ServicioOperacion.retirar(monto, idCuentaBancaria), HttpStatus.CREATED);
    }

    @PostMapping("/transferir")
    public ResponseEntity<List<Movimiento>> transferir(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria {
        String monto="";
        String idCuentaBancariaOrigen="";
        String idCuentaBancariaDestino="";
        if(datos.containsKey("monto")) {
            monto=datos.get("monto");
        }
        if(datos.containsKey("idCuentaBancariaOrigen")) {
            idCuentaBancariaOrigen=datos.get("idCuentaBancariaOrigen");
        }
        if(datos.containsKey("idCuentaBancariaDestino")) {
            idCuentaBancariaDestino=datos.get("idCuentaBancariaDestino");
        }
        return new ResponseEntity<>(ServicioOperacion.transferir(monto, idCuentaBancariaOrigen, idCuentaBancariaDestino), HttpStatus.CREATED);
    }
}
