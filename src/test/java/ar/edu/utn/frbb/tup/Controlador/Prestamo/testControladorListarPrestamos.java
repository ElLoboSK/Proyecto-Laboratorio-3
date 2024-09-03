package ar.edu.utn.frbb.tup.Controlador.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoTienePrestamo;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorListarPrestamos {
    
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
    public void testListarPrestamosExitoso() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se crea el cliente del cual se van a devolver los prestamos.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se crean los prestamos del cliente creado y se agregan a una lista.
        Prestamo prestamo=new Prestamo(0, 0, 12000, 10, 0, 12000);
        Prestamo prestamo2=new Prestamo(0, 0, 12000, 10, 0, 12000);

        List<Prestamo> prestamos=new ArrayList<>();
        prestamos.add(prestamo);
        prestamos.add(prestamo2);

        //se arma el resultado esperado a devolver por el servicio.
        Map<String, Object> resultado=new LinkedHashMap<String, Object>();
        resultado.put("numeroCliente", cliente.getDni());
        resultado.put("prestamos", prestamos);

        //Se simula el comportamiento del servicio.
        when(servicioPrestamo.listarPrestamos("0")).thenReturn(resultado);

        //Se llama al controlador y se verifican los resultados.
        assertEquals("200 OK", String.valueOf(controladorPrestamo.listarPrestamos("0").getStatusCode()));
        assertEquals(resultado, controladorPrestamo.listarPrestamos("0").getBody());
    }

    @Test
    public void testListarPrestamosNoTienePrestamos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoTienePrestamo.class).when(servicioPrestamo).listarPrestamos("0");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteNoTienePrestamo.class, () -> controladorPrestamo.listarPrestamos("0"));
    }

    @Test
    public void testListarPrestamosClienteNoExiste() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionClienteNoExiste.class).when(servicioPrestamo).listarPrestamos("0");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionClienteNoExiste.class, () -> controladorPrestamo.listarPrestamos("0"));
    }

    @Test
    public void testListarPrestamosDatosInvalidos() throws ExcepcionDatosInvalidos, ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se simula el comportamiento del servicio y se fuerza a que lance una excepcion.
        doThrow(ExcepcionDatosInvalidos.class).when(validacionDatos).intPositivoValido("0");

        //Se llama al controlador y se verifica que se haya lanzado la excepcion.
        assertThrows(ExcepcionDatosInvalidos.class, () -> controladorPrestamo.listarPrestamos("0"));
    }
}
