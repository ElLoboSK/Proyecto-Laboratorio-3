package ar.edu.utn.frbb.tup.Controlador.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorEliminarCliente {
    
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
    public void testEliminarClienteExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
        //Se crea el cliente a devolver por el servicio.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento del servicio.
        when(servicioCliente.eliminarCliente("45349054")).thenReturn(cliente);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("200 OK", String.valueOf(controladorCliente.eliminarCliente("45349054").getStatusCode()));
        assertEquals(cliente, controladorCliente.eliminarCliente("45349054").getBody());
    }

    @Test
    public void testEliminarClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoExiste.class).when(servicioCliente).eliminarCliente("45349054");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorCliente.eliminarCliente("45349054"));
    }

    @Test
    public void testEliminarClienteTienePrestamo() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteTienePrestamo.class).when(servicioCliente).eliminarCliente("45349054");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteTienePrestamo.class, () -> controladorCliente.eliminarCliente("45349054"));
    }

    @Test
    public void testEliminarClienteTieneSaldo() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteTieneSaldo.class).when(servicioCliente).eliminarCliente("45349054");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteTieneSaldo.class, () -> controladorCliente.eliminarCliente("45349054"));
    }

    @Test
    public void testEliminarClienteDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosCliente).dniValido("");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCliente.eliminarCliente(""));
    }
}
