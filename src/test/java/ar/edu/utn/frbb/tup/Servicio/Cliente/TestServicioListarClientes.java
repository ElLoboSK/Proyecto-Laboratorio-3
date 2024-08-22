package ar.edu.utn.frbb.tup.Servicio.Cliente;

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
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioListarClientes {
    
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
    public void testListarClientesExitoso() throws ExcepcionNoHayClientes, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        Cliente cliente = ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        Cliente cliente2=ServicioCliente.crearCliente("44741717", "Joaco", "Widmer", "2932502274");

        List<Cliente> clientes = ServicioCliente.listarClientes();
        assertEquals(2, clientes.size());
        assertEquals(cliente, clientes.get(0));
        assertEquals(cliente2, clientes.get(1));
    }

    @Test
    public void testListarClientesNoHayClientes() throws ExcepcionNoHayClientes{
        assertThrows(ExcepcionNoHayClientes.class, () -> ServicioCliente.listarClientes());
    }
}
