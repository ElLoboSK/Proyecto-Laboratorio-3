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

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorObtenerCliente {
    
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
    public void testObtenerClienteExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(servicioCliente.obtenerCliente("45349054")).thenReturn(cliente);
        
        assertEquals("200 OK", String.valueOf(controladorCliente.obtenerCliente("45349054").getStatusCode()));
        assertEquals(cliente, controladorCliente.obtenerCliente("45349054").getBody());
    }

    @Test
    public void testObtenerClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        doThrow(ExcepcionClienteNoExiste.class).when(servicioCliente).obtenerCliente("45349054");

        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorCliente.obtenerCliente("45349054"));
    }

    @Test
    public void testObtenerClienteDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosCliente).dniValido("");

        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCliente.obtenerCliente(""));
    }
}
