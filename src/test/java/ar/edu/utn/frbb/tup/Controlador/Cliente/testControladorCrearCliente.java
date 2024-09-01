package ar.edu.utn.frbb.tup.Controlador.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.ControladorCliente;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCliente;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorCrearCliente {
    
    @Mock
    private ServicioCliente servicioCliente;

    @Mock
    private ValidacionDatosCliente validacionDatosCliente;

    @InjectMocks
    private ControladorCliente controladorCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controladorCliente=new ControladorCliente(servicioCliente, validacionDatosCliente);
    }

    @Test
    public void testCrearClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(servicioCliente.crearCliente(datos.get("dni"), datos.get("nombre"), datos.get("apellido"), datos.get("telefono"))).thenReturn(cliente);
        
        assertEquals("201 CREATED", String.valueOf(controladorCliente.crearCliente(datos).getStatusCode()));
        assertEquals(cliente, controladorCliente.crearCliente(datos).getBody());
    }

    @Test
    public void testCrearClienteYaExiste() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        doThrow(ExcepcionClienteYaExiste.class).when(servicioCliente).crearCliente(datos.get("dni"), datos.get("nombre"), datos.get("apellido"), datos.get("telefono"));

        assertThrows(ExcepcionClienteYaExiste.class, () -> controladorCliente.crearCliente(datos));
    }

    @Test
    public void testCrearClienteDatosInvalidos() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();

        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosCliente).datosCrearCliente(datos);

        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCliente.crearCliente(datos));
    }
}
