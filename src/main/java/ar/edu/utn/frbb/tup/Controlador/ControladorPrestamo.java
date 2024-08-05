package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/prestamo")
public class ControladorPrestamo {
    @PostMapping
    public Object solicitarPrestamo(@RequestBody Map<String, String> datos) {
        String dni="";
        String plazoMeses="";
        String montoPrestamo="";
        String moneda="";
        if (datos.containsKey("numeroCliente")) {
            dni=datos.get("numeroCliente");
        }
        if (datos.containsKey("plazoMeses")) {
            plazoMeses=datos.get("plazoMeses");
        }
        if (datos.containsKey("montoPrestamo")) {
            montoPrestamo=datos.get("montoPrestamo");
        }
        if (datos.containsKey("moneda")) {
            moneda=datos.get("moneda");
        }
        Object resultado=ServicioPrestamo.solicitarPrestamo(dni, plazoMeses, montoPrestamo, moneda);
        return resultado;
    }
    
    @GetMapping("/{idCliente}")
    public Object mostrarPrestamo(@PathVariable String idCliente) {
        Object resultado=ServicioPrestamo.mostrarPrestamo(idCliente);
        return resultado;
    }
}
