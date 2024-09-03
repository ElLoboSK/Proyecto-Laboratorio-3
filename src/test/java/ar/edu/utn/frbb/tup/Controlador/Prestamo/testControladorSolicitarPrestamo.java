package ar.edu.utn.frbb.tup.Controlador.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.ControladorPrestamo;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatos;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorSolicitarPrestamo {
    
    @Mock
    private ServicioPrestamo servicioPrestamo;

    @Mock
    private ValidacionDatosPrestamo validacionDatosPrestamo;

    @Mock
    private ValidacionDatos validacionDatos;

    @InjectMocks
    private ControladorPrestamo controladorPrestamo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controladorPrestamo=new ControladorPrestamo(servicioPrestamo, validacionDatosPrestamo, validacionDatos);
    }

    @Test
    public void testSolicitarPrestamoExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos = new HashMap<>();
        datos.put("numeroCliente", "45349054");
        datos.put("plazoMeses", "10");
        datos.put("montoPrestamo", "12000");
        datos.put("moneda", "dolares");

        //Se crea el plan de pagos que se espera obtener en el resultado.
        List<Object> planPagos=new ArrayList<Object>();
        for (int j=0;j<10;j++) {
            Map<String, Object> cuota=new LinkedHashMap<String, Object>();
            cuota.put("cuotaNro", j+1);
            cuota.put("monto", 12000/10);
            planPagos.add(cuota);
        }

        //Se arma el resultado esperado a devolver por el servicio.
        Map<String, Object> resultado=new LinkedHashMap<String, Object>();
        resultado.put("estado", "Aprobado");
        resultado.put("mensaje", "El prestamo fue acreditado en su cuenta bancaria (ID: 0)");
        resultado.put("planPagos", planPagos);

        //Se simula el comportamiento del servicio.
        when(servicioPrestamo.solicitarPrestamo(datos.get("numeroCliente"), datos.get("plazoMeses"), datos.get("montoPrestamo"), datos.get("moneda"))).thenReturn(resultado);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("201 CREATED", String.valueOf(controladorPrestamo.solicitarPrestamo(datos).getStatusCode()));
        assertEquals(resultado, controladorPrestamo.solicitarPrestamo(datos).getBody());
    }

    @Test
    public void testSolicitarPrestamoClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos = new HashMap<>();
        datos.put("numeroCliente", "45349054");
        datos.put("plazoMeses", "10");
        datos.put("montoPrestamo", "12000");
        datos.put("moneda", "dolares");

        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoExiste.class).when(servicioPrestamo).solicitarPrestamo(datos.get("numeroCliente"), datos.get("plazoMeses"), datos.get("montoPrestamo"), datos.get("moneda"));

        //Se llama al controlador y se verifica que lance la excepcion.
        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorPrestamo.solicitarPrestamo(datos));
    }

    @Test
    public void testSolicitarPrestamoCuentaBancariaMonedaNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crean los datos de entrada para el controlador y se ponen en un diccionario.
        Map<String, String> datos = new HashMap<>();
        datos.put("numeroCliente", "45349054");
        datos.put("plazoMeses", "10");
        datos.put("montoPrestamo", "12000");
        datos.put("moneda", "dolares");

        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionCuentaBancariaMonedaNoExiste.class).when(servicioPrestamo).solicitarPrestamo(datos.get("numeroCliente"), datos.get("plazoMeses"), datos.get("montoPrestamo"), datos.get("moneda"));

        //Se llama al controlador y se verifica que lance la excepcion.
        assertThrows(ExcepcionCuentaBancariaMonedaNoExiste.class, () -> controladorPrestamo.solicitarPrestamo(datos));
    }

    @Test
    public void testSolicitarPrestamoDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crea un diccionario con datos invalidos.
        Map<String, String> datos = new HashMap<>();
        
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatosPrestamo).datosPrestamo(datos);

        //Se llama al controlador y se verifica que lance la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorPrestamo.solicitarPrestamo(datos));
    }
}
