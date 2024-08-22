package ar.edu.utn.frbb.tup.Servicio.Clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioCrearCliente {
    
    @Mock
    private DatosCliente datosCliente;

    @InjectMocks
    private ServicioCliente servicioCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        DatosCliente.setClientes(new ArrayList<>());
    }

    @Test
    public void testCrearClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionNoHayClientes{
        Cliente cliente=ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        assertEquals(1, ServicioCliente.listarClientes().size());
        assertEquals(cliente, ServicioCliente.obtenerCliente("45349054"));
    }

    @Test
    public void testCrearClienteYaExiste() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        assertThrows(ExcepcionClienteYaExiste.class, () -> ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274"));
    }

    @Test
    public void testCrearClienteDatosInvalidos() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.crearCliente("", "Galo", "Santopietro", "2932502274"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.crearCliente("45349054", "", "Santopietro", "2932502274"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.crearCliente("45349054", "Galo", "", "2932502274"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", ""));
    }

    @Test
    public void testCrear2ClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionNoHayClientes{
        Cliente cliente=ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        Cliente cliente2=ServicioCliente.crearCliente("44741717", "Joaco", "Widmer", "2932502274");

        assertEquals(2, ServicioCliente.listarClientes().size());
        assertEquals(cliente, ServicioCliente.obtenerCliente("45349054"));
        assertEquals(cliente2, ServicioCliente.obtenerCliente("44741717"));
    }
}
