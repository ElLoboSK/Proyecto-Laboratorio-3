package ar.edu.utn.frbb.tup.Controlador.Validacion.ValidacionDatosCliente;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCliente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestValidacionDatosModificarCliente {
        
    private ValidacionDatosCliente validacionDatosCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosCliente=new ValidacionDatosCliente();
    }

    @Test
    public void testDatosModficiarClienteExitoso() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        validacionDatosCliente.datosModificarCliente(datos);
    }

    @Test
    public void testDatosModficiarCliente1CampoExitoso() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");

        validacionDatosCliente.datosModificarCliente(datos);

        datos.remove("nombre");
        datos.put("apellido", "Santopietro");

        validacionDatosCliente.datosModificarCliente(datos);
    }

    @Test
    public void testDatosModficiarClienteFaltanCampos() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();

        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosModificarCliente(datos));
    }

    @Test
    public void testDatosModficiarClienteCamposVacios() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "");
        datos.put("nombre", "");
        datos.put("apellido", "");
        datos.put("telefono", "");

        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosModificarCliente(datos));
    }
}
