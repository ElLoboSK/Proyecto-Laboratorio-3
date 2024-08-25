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
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioCrearCuentaBancaria {
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @InjectMocks
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    //    DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
    //    DatosCliente.setClientes(new ArrayList<>());
    }

    @Test
    public void testCrearCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionNoHayCuentasBancarias, ExcepcionClienteYaExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

    //    CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

    //    assertEquals(1, ServicioCuentaBancaria.listarCuentasBancarias().size());
    //    assertEquals(cuentaBancaria, ServicioCuentaBancaria.obtenerCuentaBancaria("0"));
    }

    @Test
    public void testCrearCuentaBancariaYaExiste() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionNoHayCuentasBancarias, ExcepcionClienteYaExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

    //    ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
    //    assertThrows(ExcepcionCuentaBancariaYaExiste.class, () -> ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares"));
    }

    @Test
    public void testCrearCuentaBancariaClienteNoExiste() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionNoHayCuentasBancarias{
    //    assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares"));
    }

    @Test
    public void testCrearCuentaBancariaDatosInvalidos() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionNoHayCuentasBancarias{
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCuentaBancaria.crearCuentaBancaria("", "Caja de ahorro", "Dolares"));
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCuentaBancaria.crearCuentaBancaria("45349054", "", "Dolares"));
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", ""));
    }

    @Test
    public void testCrear2CuentasBancariasExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionNoHayCuentasBancarias, ExcepcionClienteYaExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

    //    CuentaBancaria cuentaBancaria=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
    //    CuentaBancaria cuentaBancaria2=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Cuenta corriente", "Pesos");

    //    assertEquals(2, ServicioCuentaBancaria.listarCuentasBancarias().size());
    //    assertEquals(cuentaBancaria, ServicioCuentaBancaria.obtenerCuentaBancaria("0"));
    //    assertEquals(cuentaBancaria2, ServicioCuentaBancaria.obtenerCuentaBancaria("1"));
    }
}
