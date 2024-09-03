package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosPrestamo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestValidacionDatosPrestamo {
    
    private ValidacionDatosPrestamo validacionDatosPrestamo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosPrestamo=new ValidacionDatosPrestamo();
    }

    @Test
    public void testDatosPrestamoExitoso() throws ExcepcionDatosInvalidos{
        //Se crean los datos de entrada para el test y se agregan a un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("numeroCliente", "45349054");
        datos.put("plazoMeses", "10");
        datos.put("montoPrestamo", "12000");
        datos.put("moneda", "dolares");
        
        ///Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosPrestamo.datosPrestamo(datos);
    }

    @Test
    public void testDatosPrestamoFaltanCampos() throws ExcepcionDatosInvalidos{
        //Se crea un diccionario sin campos.
        Map<String, String> datos=new HashMap<>();
        
        //Se llama al metodo a testear, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosPrestamo.datosPrestamo(datos));
    }
}
