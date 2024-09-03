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
        //Se crea el cliente que va a tener la cuenta bancaria.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteId(cliente.getId())).thenReturn(cliente);

        //Se crea la cuenta bancaria a eliminar.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
    
        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se elimina la cuenta bancaria y se devuelve la cuenta bancaria eliminada.
        CuentaBancaria cuentaBancariaEliminada=servicioCuentaBancaria.eliminarCuentaBancaria("0");

        //Se comprueba que la cuenta bancaria creada sea la misma que la cuenta bancaria que se eliminó.
        assertEquals(cuentaBancaria, cuentaBancariaEliminada);
    }

    @Test
    public void testEliminarCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        //Se llama al metodo y se espera que se lance la excepcion por una cuenta bancaria que no existe.
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaTieneSaldo() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        //Se crea la cuenta bancaria a eliminar y se le asigna un saldo.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        cuentaBancaria.setSaldo(12000);

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);
        
        //Se intenta eliminar la cuenta bancaria y se espera que se lance la excepcion por tener saldo.
        assertThrows(ExcepcionCuentaBancariaTieneSaldo.class, () -> servicioCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaConMovimientoExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaTieneSaldo, ExcepcionSaldoInsuficiente{
        //Se crea el cliente que va a tener la cuenta bancaria.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteId(cliente.getId())).thenReturn(cliente);

        //Se crea la cuenta bancaria a eliminar y se le asigna un saldo.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se crea un movimiento y se le asigna a la cuenta bancaria.
        Movimiento movimiento=new Movimiento(0, 0, LocalDate.now(), 0, "deposito");
        
        List<Movimiento> movimientos=new ArrayList<Movimiento>();
        movimientos.add(movimiento);
        cuentaBancaria.setMovimientos(movimientos);

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se elimina la cuenta bancaria y se devuelve la cuenta bancaria eliminada.
        CuentaBancaria cuentaBancariaEliminada=servicioCuentaBancaria.eliminarCuentaBancaria("0");
        
        //Se comprueba que la cuenta bancaria creada sea la misma que la cuenta bancaria que se eliminó.
        assertEquals(cuentaBancaria, cuentaBancariaEliminada);
    }
}