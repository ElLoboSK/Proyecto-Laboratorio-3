package ar.edu.utn.frbb.tup.Controlador;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

import java.util.Map;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/cliente")
public class ControladorCliente {
    
    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Map<String, String> datos) throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        String dni="";
        String nombre="";
        String apellido="";
        String telefono="";
        if (datos.containsKey("dni")) {
            dni=datos.get("dni");
        }
        if (datos.containsKey("nombre")) {
            nombre=datos.get("nombre");
        }
        if (datos.containsKey("apellido")) {
            apellido=datos.get("apellido");
        }
        if (datos.containsKey("telefono")) {
            telefono=datos.get("telefono");
        }

        return new ResponseEntity<>(ServicioCliente.crearCliente(dni, nombre, apellido, telefono), HttpStatus.CREATED);
    }

    @GetMapping("/obtener/{dni}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String dni) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos {
        return new ResponseEntity<>(ServicioCliente.obtenerCliente(dni), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes() throws ExcepcionNoHayClientes {
        return new ResponseEntity<>(ServicioCliente.listarClientes(), HttpStatus.OK);
    }

    @PutMapping("/modificar/{dni}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable String dni, @RequestBody Map<String, String> datos) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos {
        String nombre="";
        String apellido="";
        String telefono="";
        if (datos.containsKey("nombre")) {
            nombre=datos.get("nombre");
        }
        if (datos.containsKey("apellido")) {
            apellido=datos.get("apellido");
        }
        if (datos.containsKey("telefono")) {
            telefono=datos.get("telefono");
        }
        return new ResponseEntity<>(ServicioCliente.modificarCliente(dni, nombre, apellido, telefono), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<Cliente> eliminarCliente(@PathVariable String dni) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteTienePrestamo, ExcepcionClienteTieneSaldo {
        return new ResponseEntity<>(ServicioCliente.eliminarCliente(dni), HttpStatus.OK);
    }
}
