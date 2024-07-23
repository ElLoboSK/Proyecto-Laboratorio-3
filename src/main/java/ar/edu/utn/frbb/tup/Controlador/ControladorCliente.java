package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/cliente")
public class ControladorCliente {
    
    @PostMapping("/crear")
    public String crearCliente(@RequestBody Map<String, String> datos) {
        String resultado=ServicioCliente.crearCliente(datos.get("nombre"), datos.get("apellido"), datos.get("dni"), datos.get("telefono"));
        return resultado;
    }

    @GetMapping("/mostrar/{dni}")
    public Object mostrarCliente(@PathVariable String dni) {
        Object resultado=ServicioCliente.mostrarCliente(dni);
        return resultado;
    }

    @GetMapping("/listar")
    public Object listarClientes() {
        Object resultado=ServicioCliente.listarClientes();
        return resultado;
    }

    @PutMapping("/modificar/{dni}")
    public String modificarCliente(@PathVariable String dni, @RequestBody Map<String, String> datos) {
        String resultado=ServicioCliente.modificarCliente(dni, datos.get("nombre"), datos.get("apellido"), datos.get("telefono"));
        return resultado;
    }

    @DeleteMapping("/eliminar/{dni}")
    public String eliminarCliente(@PathVariable String dni){
        String resultado=ServicioCliente.eliminarCliente(dni);
        return resultado;
    }
}
