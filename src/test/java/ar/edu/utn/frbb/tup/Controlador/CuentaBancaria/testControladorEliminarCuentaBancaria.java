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
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaTieneSaldo;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorEliminarCuentaBancaria {
    
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
    public void testEliminarCuentaBancariaExitoso() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        when(servicioCuentaBancaria.eliminarCuentaBancaria("0")).thenReturn(cuentaBancaria);

        assertEquals("200 OK", String.valueOf(controladorCuentaBancaria.eliminarCuentaBancaria("0").getStatusCode()));
        assertEquals(cuentaBancaria, controladorCuentaBancaria.eliminarCuentaBancaria("0").getBody());
    }

    @Test
    public void testEliminarCuentaBancariaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        doThrow(ExcepcionCuentaBancariaNoExiste.class).when(servicioCuentaBancaria).eliminarCuentaBancaria("0");

        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> controladorCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaTieneSaldo() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        doThrow(ExcepcionCuentaBancariaTieneSaldo.class).when(servicioCuentaBancaria).eliminarCuentaBancaria("0");

        assertThrows(ExcepcionCuentaBancariaTieneSaldo.class, () -> controladorCuentaBancaria.eliminarCuentaBancaria("0"));
    }

    @Test
    public void testEliminarCuentaBancariaDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        doThrow(ExcepcionDatosInvalidos.class).when(ValidacionDatos).intPositivoValido("");

        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCuentaBancaria.eliminarCuentaBancaria(""));
    }
}
