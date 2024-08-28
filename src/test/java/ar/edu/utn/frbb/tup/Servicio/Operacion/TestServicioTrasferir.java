package ar.edu.utn.frbb.tup.Servicio.Operacion;

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

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioTrasferir {
    
    @Mock
    private DatosMovimiento datosMovimiento;

    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @InjectMocks
    private ServicioOperacion servicioOperacion;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioOperacion = new ServicioOperacion(datosMovimiento, datosCuentaBancaria);
    } 

    @Test
    public void testTransferirExitoso() throws ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        CuentaBancaria cuentaBancaria2=new CuentaBancaria(1, 0, LocalDate.now(), 0, "123456", "cuenta corriente", "dolares");
        
        cuentaBancaria.setSaldo(12000);

        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria2.getId())).thenReturn(cuentaBancaria2);

        List<Movimiento> movimientos=servicioOperacion.transferir("12000", "0", "1");

        assertEquals(0, cuentaBancaria.getSaldo());
        assertEquals(12000, cuentaBancaria2.getSaldo());
        assertEquals(1, cuentaBancaria.getMovimientos().size());
        assertEquals(1, cuentaBancaria2.getMovimientos().size());
        assertEquals("transferencia enviada", movimientos.get(0).getOperacion());
        assertEquals("transferencia recibida", movimientos.get(1).getOperacion());
        assertNotNull(movimientos);
    }

    @Test
    public void testTransferirCuentaBancariaNoExiste() throws ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste{
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioOperacion.transferir("12000", "0", "1"));
    }

    @Test
    public void testTrasnferirMismaCuentaBancaria() throws ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        assertThrows(ExcepcionMismaCuentaBancaria.class, () -> servicioOperacion.transferir("12000", "0", "0"));
    }

    @Test
    public void testTransferirMonedaDiferente() throws ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        CuentaBancaria cuentaBancaria2=new CuentaBancaria(1, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "pesos");
        
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria2.getId())).thenReturn(cuentaBancaria2);
        
        assertThrows(ExcepcionMonedaDiferente.class, () -> servicioOperacion.transferir("12000", "0", "1"));
    }

    @Test
    public void testTransferirSaldoInsuficiente() throws ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        CuentaBancaria cuentaBancaria2=new CuentaBancaria(1, 0, LocalDate.now(), 0, "123456", "cuenta corriente", "dolares");
        
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria2.getId())).thenReturn(cuentaBancaria2);

        assertThrows(ExcepcionSaldoInsuficiente.class, () -> servicioOperacion.transferir("12000", "0", "1"));
    }
}
