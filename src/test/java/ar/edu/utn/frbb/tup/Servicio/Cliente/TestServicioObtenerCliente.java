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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioObtenerCliente {
    
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
    public void testObtenerClienteExitoso() throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        Cliente clienteCreado = ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        Cliente clienteObtenido = ServicioCliente.obtenerCliente("45349054");

        assertEquals(clienteCreado, clienteObtenido);
    }

    @Test
    public void testObtenerClienteNoExiste() throws ExcepcionClienteNoExiste{
        assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioCliente.obtenerCliente("45349054"));
    }

    @Test
    public void testObtenerClienteDatosInvalidos() throws ExcepcionClienteNoExiste{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.obtenerCliente(""));
    }
}
