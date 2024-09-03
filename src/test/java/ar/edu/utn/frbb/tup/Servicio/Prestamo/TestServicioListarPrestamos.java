package ar.edu.utn.frbb.tup.Servicio.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioScoreCrediticio;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioListarPrestamos {

    @Mock
    private DatosPrestamo datosPrestamo;
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private ServicioScoreCrediticio servicioScoreCrediticio;

    @InjectMocks
    private ServicioPrestamo servicioPrestamo;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioPrestamo=new ServicioPrestamo(datosPrestamo, datosCliente, servicioScoreCrediticio);
    }

    @Test
    public void testListarPrestamosExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se crea un cliente con un prestamo asignado.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");
        Prestamo prestamo=new Prestamo(0, 0, 12000, 10, 0, 12000);

        List<Prestamo> prestamosCreados=new ArrayList<>();
        prestamosCreados.add(prestamo);
        cliente.setPrestamos(prestamosCreados);
        
        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteId(cliente.getId())).thenReturn(cliente);

        //Se hace una lista de prestamos activos del cliente creado.
        Map<String, Object> prestamosListados=servicioPrestamo.listarPrestamos(String.valueOf(cliente.getId()));

        //Se comprueba que la lista de prestamos sea la esperada.
        assertEquals(prestamosCreados, prestamosListados.get("prestamos"));
        assertEquals("45349054", String.valueOf(prestamosListados.get("numeroCliente")));
    }

    @Test
    public void testListarPrestamosClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo, ExcepcionClienteNoExiste{
        //Se llama al metodo y se espera que se lance la excepcion por la falta de cliente.
        assertThrows(ExcepcionClienteNoExiste.class, () -> servicioPrestamo.listarPrestamos("0"));
    } 
    
    @Test
    public void testListarPrestamosClienteNoTienePrestamos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste{
        //Se crea un cliente sin prestamos.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteId(cliente.getId())).thenReturn(cliente);

        //Se llama al metodo y se espera que se lance la excepcion por la falta de prestamos.
        assertThrows(ExcepcionClienteNoTienePrestamo.class, () -> servicioPrestamo.listarPrestamos("0"));
    } 
}
