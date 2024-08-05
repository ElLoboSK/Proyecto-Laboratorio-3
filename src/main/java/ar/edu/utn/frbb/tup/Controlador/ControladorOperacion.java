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
        String monto="";
        String idCuentaBancaria="";
        if(datos.containsKey("monto")) {
            monto=datos.get("monto");
        }
        if(datos.containsKey("idCuentaBancaria")) {
            idCuentaBancaria=datos.get("idCuentaBancaria");
        }
        String resultado=ServicioOperacion.depositar(monto, idCuentaBancaria);
        return resultado;
    }
    
    @PostMapping("/retirar")
    public String retirar(@RequestBody Map<String, String> datos) {
        String monto="";
        String idCuentaBancaria="";
        if(datos.containsKey("monto")) {
            monto=datos.get("monto");
        }
        if(datos.containsKey("idCuentaBancaria")) {
            idCuentaBancaria=datos.get("idCuentaBancaria");
        }
        String resultado=ServicioOperacion.retirar(monto, idCuentaBancaria);
        return resultado;
    }

    @PostMapping("/transferir")
    public String transferir(@RequestBody Map<String, String> datos) {
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
        String resultado=ServicioOperacion.transferir(monto, idCuentaBancariaOrigen, idCuentaBancariaDestino);
        return resultado;
    }
}
