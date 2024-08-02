package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/operacion")
public class ControladorOperacion {
    
    @PostMapping("/depositar")
    public String depositar(@RequestBody Map<String, String> datos) {
        String resultado=ServicioOperacion.depositar(datos.get("monto"), datos.get("idCuentaBancaria"));
        return resultado;
    }
    
    @PostMapping("/retirar")
    public String retirar(@RequestBody Map<String, String> datos) {
        String resultado=ServicioOperacion.retirar(datos.get("monto"), datos.get("idCuentaBancaria"));
        return resultado;
    }

    @PostMapping("/transferir")
    public String transferir(@RequestBody Map<String, String> datos) {
        String resultado=ServicioOperacion.transferir(datos.get("monto"), datos.get("idCuentaBancariaOrigen"), datos.get("idCuentaBancariaDestino"));
        return resultado;
    }
}
