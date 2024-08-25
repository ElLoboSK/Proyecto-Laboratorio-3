package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionEntradas;
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
    private ServicioCuentaBancaria servicioCuentaBancaria;
    private ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria;
    private ValidacionEntradas validacionEntradas;

    public ControladorCuentaBancaria(ServicioCuentaBancaria servicioCuentaBancaria, ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria, ValidacionEntradas validacionEntradas) {
        this.servicioCuentaBancaria=servicioCuentaBancaria;
        this.validacionDatosCuentaBancaria=validacionDatosCuentaBancaria;
        this.validacionEntradas=validacionEntradas;
    }

    @PostMapping("/crear")
    public ResponseEntity<CuentaBancaria> crearCuentaBancaria(@RequestBody Map<String, String> datos) throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste {
        validacionDatosCuentaBancaria.datosCrearCuentaBancaria(datos);
        return new ResponseEntity<>(servicioCuentaBancaria.crearCuentaBancaria(datos.get("dni"), datos.get("tipoCuenta"), datos.get("moneda")), HttpStatus.CREATED);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<CuentaBancaria> obtenerCuentaBancaria(@PathVariable String id) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos {
        validacionEntradas.intPositivoValido(id);
        return new ResponseEntity<>(servicioCuentaBancaria.obtenerCuentaBancaria(id), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CuentaBancaria>> listarCuentasBancarias() throws ExcepcionNoHayCuentasBancarias {
        return new ResponseEntity<>(servicioCuentaBancaria.listarCuentasBancarias(), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<CuentaBancaria> eliminarCuentaBancaria(@PathVariable String id) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionCuentaBancariaTieneSaldo {
        validacionEntradas.intPositivoValido(id);
        return new ResponseEntity<>(servicioCuentaBancaria.eliminarCuentaBancaria(id), HttpStatus.OK);
    }
}
