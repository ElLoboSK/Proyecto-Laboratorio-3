package ar.edu.utn.frbb.tup.Servicio.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioObtenerCuentaBancaria {
    
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
    public void testObtenerCuentaBancariaExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste{
        //Se crea la cuenta bancaria.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se simula el comportamiento de la base de datos.
        when(datosCuentaBancaria.buscarCuentaBancariaId(cuentaBancaria.getId())).thenReturn(cuentaBancaria);

        //Se obtiene la cuenta bancaria.
        CuentaBancaria cuentaBancariaObtenida=servicioCuentaBancaria.obtenerCuentaBancaria("0");

        //Se comprueba que la cuenta bancaria obtenida sea la misma que la creada.
        assertEquals(cuentaBancaria, cuentaBancariaObtenida);
        assertNotNull(cuentaBancariaObtenida);
    }

    @Test
    public void testObtenerCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaNoExiste{
        //Se llama al servicio para obtener la cuenta bancaria y se comprueba que se lance la excepcion por no existir la cuenta bancaria.
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> servicioCuentaBancaria.obtenerCuentaBancaria("0"));
    }
}
