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
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorModificarCliente {
    
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
    public void testModificarClienteExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        //Se crea el cliente a devolver por el servicio.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la validacion de datos.
        when(validacionDatosCliente.datosModificarCliente(datos)).thenReturn(datos);

        //Se simula el comportamiento del servicio.
        when(servicioCliente.modificarCliente(datos.get("dni"), datos.get("nombre"), datos.get("apellido"), datos.get("telefono"))).thenReturn(cliente);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("200 OK", String.valueOf(controladorCliente.modificarCliente(datos).getStatusCode()));
        assertEquals(cliente, controladorCliente.modificarCliente(datos).getBody());
    }

    @Test
    public void testModificarClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        //Se simula el comportamiento de la validacion de datos.
        when(validacionDatosCliente.datosModificarCliente(datos)).thenReturn(datos);

        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoExiste.class).when(servicioCliente).modificarCliente(datos.get("dni"), datos.get("nombre"), datos.get("apellido"), datos.get("telefono"));

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorCliente.modificarCliente(datos));
    }

    @Test
    public void testModificarClienteDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        //Se crea un diccionario con datos invalidos.
        Map<String, String> datos=new HashMap<>();

        //Se simula el comportamiento de la validacion de datos y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosCliente).datosModificarCliente(datos);

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCliente.modificarCliente(datos));
    }
}
