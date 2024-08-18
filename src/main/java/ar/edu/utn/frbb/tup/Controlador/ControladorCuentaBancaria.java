package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Controlador.Precesadores.ProcesadorDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/cuenta")
public class ControladorCuentaBancaria {
    
    @PostMapping("/crear")
    public ResponseEntity<CuentaBancaria> crearCuentaBancaria(@RequestBody Map<String, String> datosCuentaBancaria) throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste {
        Map<String, String> datos = ProcesadorDatosCuentaBancaria.datosCuentaBancaria(datosCuentaBancaria);
        return new ResponseEntity<>(ServicioCuentaBancaria.crearCuentaBancaria(datos.get("dni"), datos.get("tipoCuenta"), datos.get("moneda")), HttpStatus.CREATED);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<CuentaBancaria> obtenerCuentaBancaria(@PathVariable String id) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos {
        return new ResponseEntity<>(ServicioCuentaBancaria.obtenerCuentaBancaria(id), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CuentaBancaria>> listarCuentasBancarias() throws ExcepcionNoHayCuentasBancarias {
        return new ResponseEntity<>(ServicioCuentaBancaria.listarCuentasBancarias(), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<CuentaBancaria> eliminarCuentaBancaria(@PathVariable String id) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionCuentaBancariaTieneSaldo {
        return new ResponseEntity<>(ServicioCuentaBancaria.eliminarCuentaBancaria(id), HttpStatus.OK);
    }
}
