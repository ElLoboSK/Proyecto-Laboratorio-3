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

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioObtenerCuentaBancaria {
    
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
    public void testObtenerCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
    //    CuentaBancaria cuentaBancariaCreada=ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");

    //    CuentaBancaria cuentaBancariaObtenida=ServicioCuentaBancaria.obtenerCuentaBancaria("0");

    //    assertEquals(cuentaBancariaCreada, cuentaBancariaObtenida);
    }

    @Test
    public void testObtenerCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos{
    //    assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> ServicioCuentaBancaria.obtenerCuentaBancaria("0"));
    }

    @Test
    public void testObtenerCuentaBancariaDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCuentaBancaria.obtenerCuentaBancaria(""));
    }
}
