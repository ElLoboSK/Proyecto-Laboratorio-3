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

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioDepositar {
    
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
    public void testDepositarExitoso() throws ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        //Se crea la cuenta bancaria con la que se va a realizar la operacion.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se realiza la operacion.
        Movimiento movimiento=servicioOperacion.depositar("12000", "0");

        //Se verifica que la operacion se haya realizado correctamente.
        assertEquals(12000, cuentaBancaria.getSaldo());
        assertEquals(1, cuentaBancaria.getMovimientos().size());
        assertEquals("deposito", movimiento.getOperacion());
        assertNotNull(movimiento);
    }

    @Test
    public void testDepositarCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste{
        //Se llama al metodo y se verifica que se lance la excepcion esperada por la falta de la cuenta bancaria.
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioOperacion.depositar("12000", "0"));
    }
}
