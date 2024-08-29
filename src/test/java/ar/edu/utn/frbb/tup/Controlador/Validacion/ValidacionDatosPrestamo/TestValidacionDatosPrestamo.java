package ar.edu.utn.frbb.tup.Controlador.Validacion.ValidacionDatosPrestamo;

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
        Map<String, String> datos=new HashMap<>();
        datos.put("numeroCliente", "45349054");
        datos.put("plazoMeses", "10");
        datos.put("montoPrestamo", "12000");
        datos.put("moneda", "dolares");
        
        validacionDatosPrestamo.datosPrestamo(datos);
    }

    @Test
    public void testDatosPrestamoFaltanCampos() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosPrestamo.datosPrestamo(datos));
    }
}
