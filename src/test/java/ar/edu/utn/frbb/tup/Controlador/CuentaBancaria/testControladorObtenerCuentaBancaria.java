package ar.edu.utn.frbb.tup.Controlador.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.ControladorCuentaBancaria;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatos;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorObtenerCuentaBancaria {
            
    @Mock
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @Mock
    private ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria;

    @Mock
    private ValidacionDatos ValidacionDatos;

    @InjectMocks
    private ControladorCuentaBancaria controladorCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controladorCuentaBancaria=new ControladorCuentaBancaria(servicioCuentaBancaria, validacionDatosCuentaBancaria, ValidacionDatos);
    }

    @Test
    public void testObtenerCuentaBancariaExitoso() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        //Se crea la cuenta bancaria a devolver por el servicio.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se simula el comportamiento del servicio.
        when(servicioCuentaBancaria.obtenerCuentaBancaria("0")).thenReturn(cuentaBancaria);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("200 OK", String.valueOf(controladorCuentaBancaria.obtenerCuentaBancaria("0").getStatusCode()));
        assertEquals(cuentaBancaria, controladorCuentaBancaria.obtenerCuentaBancaria("0").getBody());
    }

    @Test
    public void testObtenerCuentaBancariaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionCuentaBancariaNoExiste.class).when(servicioCuentaBancaria).obtenerCuentaBancaria("0");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> controladorCuentaBancaria.obtenerCuentaBancaria("0"));
    }

    @Test
    public void testObtenerCuentaBancariaDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(ValidacionDatos).intPositivoValido("");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCuentaBancaria.obtenerCuentaBancaria(""));
    }
}
