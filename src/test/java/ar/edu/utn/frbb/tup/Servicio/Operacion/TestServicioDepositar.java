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

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioDepositar {
    
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
    //    DatosMovimiento.setMovimientos(new ArrayList<>());
    //    DatosCliente.setClientes(new ArrayList<>());
    //    DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
    }

    @Test
    public void testDepositarExitoso() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
    //    CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

    //    ServicioOperacion.depositar("12000", "0");

    //    assertEquals(12000, cuentaBancaria.getSaldo());
    }

    @Test
    public void testDepositarCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos{
    //    assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioOperacion.depositar("12000", "0"));
    }

    @Test
    public void testDepositarDatosInvalidos() throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos{
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioOperacion.depositar("12000", ""));
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioOperacion.depositar("", "0"));
    }
}
