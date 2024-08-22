package ar.edu.utn.frbb.tup.Servicio.Operacion;

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

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioTrasferir {
    
    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosMovimiento datosMovimiento;

    @InjectMocks
    private ServicioOperacion servicioOperacion;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        DatosMovimiento.setMovimientos(new ArrayList<>());
        DatosCliente.setClientes(new ArrayList<>());
        DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
    }   

    @Test
    public void testTransferirExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        CuentaBancaria cuentaBancariaOrigen=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        CuentaBancaria cuentaBancariaDestino=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Cuenta corriente", "Dolares");
        
        ServicioOperacion.depositar("12000", "0");
        ServicioOperacion.transferir("12000", "0", "1");

        assertEquals(0, cuentaBancariaOrigen.getSaldo());
        assertEquals(12000, cuentaBancariaDestino.getSaldo());
    }

    @Test
    public void testTransferirDatosInvalidos() throws ExcepcionDatosInvalidos{
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioOperacion.transferir("", "0", "1"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioOperacion.transferir("12000", "", "1"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioOperacion.transferir("12000", "0", ""));
    }

    @Test
    public void testTransferirCuentaBancariaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioOperacion.transferir("12000", "0", "1"));
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioOperacion.transferir("12000", "1", "0"));
    }

    @Test
    public void testTrasnferirMismaCuentaBancaria() throws ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

        assertThrows(ExcepcionMismaCuentaBancaria.class, () -> ServicioOperacion.transferir("12000", "0", "0"));
    }

    @Test
    public void testTransferirMonedaDiferente() throws ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Cuenta corriente", "Pesos");

        assertThrows(ExcepcionMonedaDiferente.class, () -> ServicioOperacion.transferir("12000", "0", "1"));
    }

    @Test
    public void testTransferirSaldoInsuficiente() throws ExcepcionDatosInvalidos, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
        ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Cuenta corriente", "Dolares");

        assertThrows(ExcepcionSaldoInsuficiente.class, () -> ServicioOperacion.transferir("12000", "0", "1"));
    }
}
