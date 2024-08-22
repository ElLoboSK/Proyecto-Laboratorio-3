package ar.edu.utn.frbb.tup.Servicio.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioSolicitarPrestamo {
    
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
    public void testSolicitarPrestamoExitosoCajaDeAhorroAceptado() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        
        ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");
        
        assertEquals(1, DatosPrestamo.getPrestamos().size());
        assertEquals(12000, cuentaBancaria.getSaldo());
    }
    
    @Test
    public void testSolicitarPrestamoExitosoCajaDeAhorroRechazado() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        ServicioCliente.crearCliente("45349055", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349055", "Caja de ahorro", "Dolares");

        ServicioPrestamo.solicitarPrestamo("45349055", "10", "12000", "Dolares");

        assertEquals(0, DatosPrestamo.getPrestamos().size());
        assertEquals(0, cuentaBancaria.getSaldo());
    }

    @Test
    public void testSolicitarPrestamoExitosoCuentaCorrienteAceptado() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Cuenta corriente", "Dolares");

        ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");

        assertEquals(1, DatosPrestamo.getPrestamos().size());
        assertEquals(12000, cuentaBancaria.getSaldo());
    }

    @Test
    public void testSolicitarPrestamoDatosInvalidos() throws ExcepcionDatosInvalidos{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioPrestamo.solicitarPrestamo("", "10", "12000", "Dolares"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioPrestamo.solicitarPrestamo("45349054", "", "12000", "Dolares"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioPrestamo.solicitarPrestamo("45349054", "10", "", "Dolares"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", ""));
    }

    @Test
    public void testSolicitarPrestamoClienteNoExiste() throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos{
        assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares"));
    }

    @Test
    public void testSolicitarPrestamoCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaMonedaNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

        assertThrows(ExcepcionCuentaBancariaMonedaNoExiste.class, () -> ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares"));
    }

    @Test
    public void testSolicitar2PrestamosExitosoCajaDeAhorro() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

        ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");
        ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");
        
        assertEquals(2, DatosPrestamo.getPrestamos().size());
        assertEquals(24000, cuentaBancaria.getSaldo());
    }
}
