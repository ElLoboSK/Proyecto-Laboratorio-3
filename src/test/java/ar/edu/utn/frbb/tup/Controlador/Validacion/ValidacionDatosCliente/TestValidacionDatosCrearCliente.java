package ar.edu.utn.frbb.tup.Controlador.Validacion.ValidacionDatosCliente;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestValidacionDatosCrearCliente {
    
    private ValidacionDatosCliente validacionDatosCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosCliente=new ValidacionDatosCliente();
    }

    @Test
    public void testDatosCrearClienteExitoso() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        validacionDatosCliente.datosCrearCliente(datos);
    }

    @Test
    public void testDatosCrearClienteFaltanCampos() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();

        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosCrearCliente(datos));
    }

    @Test
    public void testDatosCrearClienteCamposVacios() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "");
        datos.put("apellido", "");
        datos.put("telefono", "");

        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosCrearCliente(datos));
    }
}
