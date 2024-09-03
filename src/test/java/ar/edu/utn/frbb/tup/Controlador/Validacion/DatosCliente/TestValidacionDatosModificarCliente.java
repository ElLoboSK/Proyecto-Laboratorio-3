package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosCliente;

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
        //Se cren los datos de entrada para el test y se agregan a un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");
        datos.put("apellido", "Santopietro");
        datos.put("telefono", "2932502274");

        //Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCliente.datosModificarCliente(datos);
    }

    @Test
    public void testDatosModficiarCliente1CampoExitoso() throws ExcepcionDatosInvalidos{
        //Se cren los datos de entrada para el test y se agregan a un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("nombre", "Galo");

        //Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCliente.datosModificarCliente(datos);

        //Se elimina un campo y se agrega otro.
        datos.remove("nombre");
        datos.put("apellido", "Santopietro");

        //Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCliente.datosModificarCliente(datos);
    }

    @Test
    public void testDatosModficiarClienteFaltanCampos() throws ExcepcionDatosInvalidos{
        //Se crea un diccionario sin campos.
        Map<String, String> datos=new HashMap<>();

        ///Se llama al metodo a testear, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosModificarCliente(datos));
    }

    @Test
    public void testDatosModficiarClienteCamposVacios() throws ExcepcionDatosInvalidos{
        //Se crea un diccionario con campos vacios.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "");
        datos.put("nombre", "");
        datos.put("apellido", "");
        datos.put("telefono", "");

        ///Se llama al metodo a testear, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, ()-> validacionDatosCliente.datosModificarCliente(datos));
    }
}
