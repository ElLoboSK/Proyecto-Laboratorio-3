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
        Object resultado=ServicioPrestamo.solicitarPrestamo(datos.get("numeroCliente"), datos.get("plazoMeses"), datos.get("montoPrestamo"), datos.get("moneda"));
        return resultado;
    }
    @GetMapping("/{idCliente}")
    public Object mostrarPrestamo(@PathVariable String idCliente) {
        Object resultado=ServicioPrestamo.mostrarPrestamo(idCliente);
        return resultado;
    }
}
