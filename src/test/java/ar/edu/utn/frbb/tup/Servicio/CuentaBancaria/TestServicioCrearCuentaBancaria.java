package ar.edu.utn.frbb.tup.Servicio.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioCrearCuentaBancaria {
    
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
    public void testCrearCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionNoHayCuentasBancarias, ExcepcionClienteYaExiste{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);

        CuentaBancaria cuentaBancaria=servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");

        verify(datosCuentaBancaria, times(1)).agregarCuentaBancaria(cuentaBancaria);
        assertEquals(0, cuentaBancaria.getId());
        assertNotNull(cuentaBancaria);
    }

    @Test
    public void testCrearCuentaBancariaYaExiste() throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionNoHayCuentasBancarias, ExcepcionClienteYaExiste{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);

        servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");

        assertThrows(ExcepcionCuentaBancariaYaExiste.class, () -> servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares"));
    }

    @Test
    public void testCrearCuentaBancariaClienteNoExiste() throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionNoHayCuentasBancarias{
        assertThrows(ExcepcionClienteNoExiste.class, () -> servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares"));
    }
}
