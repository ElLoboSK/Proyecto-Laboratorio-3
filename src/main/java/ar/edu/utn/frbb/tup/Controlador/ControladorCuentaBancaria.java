package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/cuenta")
public class ControladorCuentaBancaria {
    
    @PostMapping("/crear")
    public String crearCuentaBancaria(@RequestBody Map<String, String> datos) {
        String dni="";
        String tipoCuenta="";
        String moneda="";
        if (datos.containsKey("dni")) {
            dni=datos.get("dni");
        }
        if (datos.containsKey("tipoCuenta")) {
            tipoCuenta=datos.get("tipoCuenta");
        }
        if (datos.containsKey("moneda")) {
            moneda=datos.get("moneda");
        }
        String resultado=ServicioCuentaBancaria.crearCuentaBancaria(dni, tipoCuenta, moneda);
        return resultado;
    }

    @GetMapping("/mostrar/{id}")
    public Object mostrarCuentaBancaria(@PathVariable String id) {
        Object resultado=ServicioCuentaBancaria.obtenerCuentaBancaria(id);
        return resultado;
    }

    @GetMapping("/listar")
    public Object listarCuentasBancarias() {
        Object resultado=ServicioCuentaBancaria.listarCuentasBancarias();
        return resultado;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarCuentaBancaria(@PathVariable String id) {
        String resultado=ServicioCuentaBancaria.eliminarCuentaBancaria(id);
        return resultado;
    }
}
