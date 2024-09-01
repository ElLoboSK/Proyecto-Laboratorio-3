package ar.edu.utn.frbb.tup.Servicio.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioListarClientes {
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @Mock
    private DatosMovimiento datosMovimiento;

    @InjectMocks
    private ServicioCliente servicioCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioCliente = new ServicioCliente(datosCliente,datosCuentaBancaria, datosMovimiento);
    }

    @Test
    public void testListarClientesExitoso() throws ExcepcionNoHayClientes, ExcepcionClienteYaExiste{
        Cliente cliente = servicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        Cliente cliente2 = servicioCliente.crearCliente("44741717", "Joaco", "Widmer", "2932502274");

        List<Cliente> clientesCreados=new ArrayList<>();
        clientesCreados.add(cliente);
        clientesCreados.add(cliente2);

        when(datosCliente.listarClientes()).thenReturn(clientesCreados);

        List<Cliente> clientesListados = servicioCliente.listarClientes();

        assertEquals(2, clientesListados.size());
        assertEquals(clientesCreados.get(0), clientesListados.get(0));
        assertEquals(clientesCreados.get(1), clientesListados.get(1));
        assertNotNull(clientesListados);
    }

    @Test
    public void testListarClientesNoHayClientes() throws ExcepcionNoHayClientes{
        assertThrows(ExcepcionNoHayClientes.class, () -> servicioCliente.listarClientes());
    }
}
