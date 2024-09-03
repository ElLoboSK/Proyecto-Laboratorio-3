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
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioRetirar {
    
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
    public void testRetirarExitoso() throws ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionSaldoInsuficiente{
        //Se crea la cuenta bancaria con la que se va a realizar la operacion.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        cuentaBancaria.setSaldo(12000);

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se realiza la operacion.
        Movimiento movimiento=servicioOperacion.retirar("12000", "0");

        //Se verifica que la operacion se haya realizado correctamente.
        assertEquals(0, cuentaBancaria.getSaldo());
        assertEquals(1, cuentaBancaria.getMovimientos().size());
        assertEquals("retiro", movimiento.getOperacion());
        assertNotNull(movimiento);
    }

    @Test
    public void testRetirarCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente{
        //Se llama al metodo y se verifica que se lance la excepcion esperada por la falta de la cuenta bancaria.
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioOperacion.retirar("12000", "0"));
    }

    @Test
    public void testRetirarSaldoInsuficiente() throws ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionSaldoInsuficiente{
        //Se crea la cuenta bancaria con la que se va a realizar la operacion.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se llama al metodo y se verifica que se lance la excepcion esperada por el saldo insuficiente.
        assertThrows(ExcepcionSaldoInsuficiente.class, () -> servicioOperacion.retirar("12000", "0"));
    }
}
