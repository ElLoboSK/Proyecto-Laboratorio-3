package ar.edu.utn.frbb.tup.Servicio.CuentaBancaria;

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

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioEliminarCuentaBancaria {
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @Mock
    private DatosMovimiento datosMovimiento;

    @InjectMocks
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
        DatosCliente.setClientes(new ArrayList<>());
        DatosMovimiento.setMovimientos(new ArrayList<>());
    }

    @Test
    public void testEliminarCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancariaCreada=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

        CuentaBancaria cuentaBancariaEliminada=ServicioCuentaBancaria.eliminarCuentaBancaria("0");

        assertEquals(cuentaBancariaCreada, cuentaBancariaEliminada);
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioCuentaBancaria.obtenerCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCuentaBancaria.eliminarCuentaBancaria(""));
    }

    @Test
    public void testEliminarCuentaBancariaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaTieneSaldo() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        ServicioOperacion.depositar("12000", "0");

        assertThrows(ExcepcionCuentaBancariaTieneSaldo.class, () -> ServicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaConMovimientosExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo, ExcepcionSaldoInsuficiente{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancariaCreada=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        ServicioOperacion.depositar("12000", "0");
        ServicioOperacion.retirar("12000", "0");

        CuentaBancaria cuentaBancariaEliminada=ServicioCuentaBancaria.eliminarCuentaBancaria("0");

        assertEquals(cuentaBancariaCreada, cuentaBancariaEliminada);
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioCuentaBancaria.obtenerCuentaBancaria("0"));
    }
}