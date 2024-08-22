package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;

import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/prestamo")
public class ControladorPrestamo {
    @PostMapping
    public ResponseEntity<Map<String, Object>> solicitarPrestamo(@RequestBody Map<String, String> datosPrestamo) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionCuentaBancariaMonedaNoExiste {
        Map<String, String> datos = ValidacionDatosPrestamo.datosPrestamo(datosPrestamo);
        return new ResponseEntity<>(ServicioPrestamo.solicitarPrestamo(datos.get("numeroCliente"), datos.get("plazoMeses"), datos.get("montoPrestamo"), datos.get("moneda")), HttpStatus.CREATED);
    }
    
    @GetMapping("/{idCliente}")
    public ResponseEntity<Map<String, Object>> listarPrestamos(@PathVariable String idCliente) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo {
        return new ResponseEntity<>(ServicioPrestamo.listarPrestamos(idCliente), HttpStatus.OK);
    }
}
