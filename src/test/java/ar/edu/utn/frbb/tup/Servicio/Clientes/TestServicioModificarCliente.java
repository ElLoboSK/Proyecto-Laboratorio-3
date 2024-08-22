package ar.edu.utn.frbb.tup.Servicio.Clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioModificarCliente {
    
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
    public void testModificarClienteExitoso() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        Cliente clienteCreado=ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        
        Cliente clienteModificado=ServicioCliente.modificarCliente("45349054", "Joaco", "Widmer", "2932504747");

        assertEquals(clienteCreado, clienteModificado);
        assertEquals(clienteModificado.getNombre(), "Joaco");
        assertEquals(clienteModificado.getApellido(), "Widmer");
        assertEquals(clienteModificado.getTelefono(), "2932504747");
    }

    @Test
    public void testModificarClienteNoExiste() throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos{
        assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioCliente.modificarCliente("45349054", "Joaco", "Widmer", "2932504747"));
    }

    @Test
    public void testModificarClienteDatosInvalidos() throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.modificarCliente("", "Joaco", "Widmer", "2932504747"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.modificarCliente("45349054", "", "", ""));
    }

    @Test
    public void testModificarCliente1DatoExitoso() throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteYaExiste{
        Cliente clienteCreado=ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        ServicioCliente.modificarCliente("45349054", "", "Widmer", "2932504747");
        ServicioCliente.modificarCliente("45349054", "Joaco", "", "2932504747");
        ServicioCliente.modificarCliente("45349054", "Joaco", "Widmer", "");
        Cliente clienteModificado=ServicioCliente.obtenerCliente("45349054");

        assertEquals(clienteCreado, clienteModificado);
        assertEquals(clienteModificado.getNombre(), "Joaco");
        assertEquals(clienteModificado.getApellido(), "Widmer");
        assertEquals(clienteModificado.getTelefono(), "2932504747");
    }
}
