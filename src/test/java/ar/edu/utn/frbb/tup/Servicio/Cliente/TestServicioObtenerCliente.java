package ar.edu.utn.frbb.tup.Servicio.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioObtenerCliente {
    
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
    public void testObtenerClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionClienteNoExiste{
        //Se crea el cliente a obtener.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);

        //Se obtiene el cliente.
        Cliente clienteObtenido = servicioCliente.obtenerCliente("45349054");

        ///Se verifica que el cliente obtenido sea el mismo que el creado.
        assertEquals(cliente, clienteObtenido);
        assertNotNull(clienteObtenido);
    }

    @Test
    public void testObtenerClienteNoExiste() throws ExcepcionClienteNoExiste{
        //Se llama al metodo a testear y se verifica que se lance la excepcion esperada por un cliente que no existe.
        assertThrows(ExcepcionClienteNoExiste.class, () -> servicioCliente.obtenerCliente("45349054"));
    }
}
