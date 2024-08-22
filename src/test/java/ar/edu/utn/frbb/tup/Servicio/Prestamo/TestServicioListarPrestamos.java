package ar.edu.utn.frbb.tup.Servicio.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
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
    private DatosCuentaBancaria datosCuentaBancaria;
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosPrestamo datosPrestamo;

    @InjectMocks
    private ServicioPrestamo servicioPrestamo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        DatosPrestamo.setPrestamos(new ArrayList<>());
        DatosCliente.setClientes(new ArrayList<>());
        DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
    }

    @Test
    public void testListarPrestamosExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste, ExcepcionClienteNoTienePrestamo{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        
        ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");

        Map<String, Object> prestamos=ServicioPrestamo.listarPrestamos("0");

        assertEquals(prestamos.get("prestamos"), DatosPrestamo.getPrestamos());
    }

    @Test
    public void testListarPrestamosDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo, ExcepcionClienteNoExiste{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioPrestamo.listarPrestamos(""));
    }

    @Test
    public void testListarPrestamosClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo, ExcepcionClienteNoExiste{
        assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioPrestamo.listarPrestamos("0"));
    } 
    
    @Test
    public void testListarPrestamosClienteNoTienePrestamos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoTienePrestamo, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        assertThrows(ExcepcionClienteNoTienePrestamo.class, () -> ServicioPrestamo.listarPrestamos("0"));
    } 
}
