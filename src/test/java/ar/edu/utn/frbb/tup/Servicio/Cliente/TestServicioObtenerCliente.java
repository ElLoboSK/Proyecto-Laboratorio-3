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
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioObtenerCliente {
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @InjectMocks
    private ServicioCliente servicioCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioCliente = new ServicioCliente(datosCliente,datosCuentaBancaria);
    }

    @Test
    public void testObtenerClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        Cliente clienteCreado = servicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        when(datosCliente.buscarClienteDni(45349054)).thenReturn(clienteCreado);

        Cliente clienteObtenido = servicioCliente.obtenerCliente("45349054");

        assertEquals(clienteCreado, clienteObtenido);
        assertNotNull(clienteObtenido);
    }

    @Test
    public void testObtenerClienteNoExiste() throws ExcepcionClienteNoExiste{
        assertThrows(ExcepcionClienteNoExiste.class, () -> servicioCliente.obtenerCliente("45349054"));
    }
}
