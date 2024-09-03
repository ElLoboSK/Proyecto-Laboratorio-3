package ar.edu.utn.frbb.tup.Controlador.CuentaBancaria;

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

import ar.edu.utn.frbb.tup.Controlador.ControladorCuentaBancaria;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatos;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorCrearCuentaBancaria {
    
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
    public void testCrearCuentaBancariaExitoso() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("tipoCuenta", "caja de ahorro");
        datos.put("moneda", "dolares");

        //Se crea la cuenta bancaria a devolver por el servicio.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");

        //Se simula el comportamiento del servicio.
        when(servicioCuentaBancaria.crearCuentaBancaria(datos.get("dni"), datos.get("tipoCuenta"), datos.get("moneda"))).thenReturn(cuentaBancaria);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("201 CREATED", String.valueOf(controladorCuentaBancaria.crearCuentaBancaria(datos).getStatusCode()));
        assertEquals(cuentaBancaria, controladorCuentaBancaria.crearCuentaBancaria(datos).getBody());
    }

    @Test
    public void testCrearCuentaBancariaYaExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("tipoCuenta", "caja de ahorro");
        datos.put("moneda", "dolares");

        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionCuentaBancariaYaExiste.class).when(servicioCuentaBancaria).crearCuentaBancaria(datos.get("dni"), datos.get("tipoCuenta"), datos.get("moneda"));

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionCuentaBancariaYaExiste.class, () -> controladorCuentaBancaria.crearCuentaBancaria(datos));
    }

    @Test
    public void testCrearCuentaBancariaClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("tipoCuenta", "caja de ahorro");
        datos.put("moneda", "dolares");

        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoExiste.class).when(servicioCuentaBancaria).crearCuentaBancaria(datos.get("dni"), datos.get("tipoCuenta"), datos.get("moneda"));

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorCuentaBancaria.crearCuentaBancaria(datos));
    }

    @Test
    public void testCrearCuentaBancariaDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        //Se crea un diccionario con datos invalidos.
        Map<String, String> datos=new HashMap<>();
        
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosCuentaBancaria).datosCrearCuentaBancaria(datos);

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorCuentaBancaria.crearCuentaBancaria(datos));
    }
}
