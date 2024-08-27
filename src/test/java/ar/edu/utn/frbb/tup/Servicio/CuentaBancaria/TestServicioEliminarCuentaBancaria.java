package ar.edu.utn.frbb.tup.Servicio.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
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
    private DatosCuentaBancaria datosCuentaBancaria;
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosMovimiento datosMovimiento;

    @InjectMocks
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioCuentaBancaria = new ServicioCuentaBancaria(datosCuentaBancaria, datosCliente, datosMovimiento);
    }

    @Test
    public void testEliminarCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(45349054)).thenReturn(cliente);
        when(datosCliente.buscarClienteId(0)).thenReturn(cliente);

        CuentaBancaria cuentaBancariaCreada=servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");
    
        when(datosCuentaBancaria.buscarCuentaBancariaId(0)).thenReturn(cuentaBancariaCreada);

        CuentaBancaria cuentaBancariaEliminada=servicioCuentaBancaria.eliminarCuentaBancaria("0");

        assertEquals(cuentaBancariaCreada, cuentaBancariaEliminada);
    }

    @Test
    public void testEliminarCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaTieneSaldo() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(45349054)).thenReturn(cliente);

        CuentaBancaria cuentaBancariaCreada=servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");
        cuentaBancariaCreada.setSaldo(12000);
    
        when(datosCuentaBancaria.buscarCuentaBancariaId(0)).thenReturn(cuentaBancariaCreada);

        assertThrows(ExcepcionCuentaBancariaTieneSaldo.class, () -> servicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaConMovimientoExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo, ExcepcionSaldoInsuficiente{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(45349054)).thenReturn(cliente);
        when(datosCliente.buscarClienteId(0)).thenReturn(cliente);

        CuentaBancaria cuentaBancariaCreada=servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");

        Movimiento movimiento=new Movimiento(0, 0, LocalDate.now(), 0, "deposito");
        
        List<Movimiento> movimientos=new ArrayList<Movimiento>();
        movimientos.add(movimiento);
        cuentaBancariaCreada.setMovimientos(movimientos);

        when(datosCuentaBancaria.buscarCuentaBancariaId(0)).thenReturn(cuentaBancariaCreada);

        CuentaBancaria cuentaBancariaEliminada=servicioCuentaBancaria.eliminarCuentaBancaria("0");
        
        assertEquals(cuentaBancariaCreada, cuentaBancariaEliminada);
    }
}