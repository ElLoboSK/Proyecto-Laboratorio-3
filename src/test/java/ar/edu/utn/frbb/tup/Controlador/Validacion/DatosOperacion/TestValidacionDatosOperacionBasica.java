package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosOperacion;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosOperacion;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestValidacionDatosOperacionBasica {
    
    private ValidacionDatosOperacion validacionDatosOperacion;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosOperacion=new ValidacionDatosOperacion();
    }

    @Test
    public void testDatosOperacionBasicaExitoso() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("monto", "12000");
        datos.put("idCuentaBancaria", "0");

        validacionDatosOperacion.datosOperacionBasica(datos);
    }

    @Test
    public void testDatosOperacionBasicaFaltanCampos() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();

        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosOperacion.datosOperacionBasica(datos));
    }
}
