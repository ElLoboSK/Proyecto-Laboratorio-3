package ar.edu.utn.frbb.tup.Controlador.Operacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.ControladorOperacion;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosOperacion;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorDepositar {

    @Mock
    private ServicioOperacion servicioOperacion;

    @Mock
    private ValidacionDatosOperacion validacionDatosOperacion;

    @InjectMocks
    private ControladorOperacion controladorOperacion;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controladorOperacion=new ControladorOperacion(servicioOperacion, validacionDatosOperacion);
    }

    @Test
    public void testDepositarExitoso() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        Map<String, String> datos = new HashMap<>();
        datos.put("monto", "12000");
        datos.put("idCuentaBancaria", "0");

        Movimiento movimiento=new Movimiento(0, 0, LocalDate.now(), 12000, "deposito");

        when(servicioOperacion.depositar(datos.get("monto"), datos.get("idCuentaBancaria"))).thenReturn(movimiento);

        assertEquals("201 CREATED", String.valueOf(controladorOperacion.depositar(datos).getStatusCode()));
        assertEquals(movimiento, controladorOperacion.depositar(datos).getBody());
    }

    @Test
    public void testDepositarCuentaBancariaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        Map<String, String> datos = new HashMap<>();
        datos.put("monto", "12000");
        datos.put("idCuentaBancaria", "0");

        doThrow(ExcepcionCuentaBancariaNoExiste.class).when(servicioOperacion).depositar(datos.get("monto"), datos.get("idCuentaBancaria"));

        assertThrows(ExcepcionCuentaBancariaNoExiste.class, () -> controladorOperacion.depositar(datos));
    }

    @Test
    public void testDepositarDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaNoExiste{
        Map<String, String> datos = new HashMap<>();

        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosOperacion).datosOperacionBasica(datos);

        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorOperacion.depositar(datos));
    }
}
