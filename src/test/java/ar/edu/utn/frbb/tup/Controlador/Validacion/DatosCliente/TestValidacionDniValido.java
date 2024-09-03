package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosCliente;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class TestValidacionDniValido {
    
    private ValidacionDatosCliente validacionDatosCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosCliente=new ValidacionDatosCliente();
    }

    @Test
    public void testDniValidoExitoso() throws ExcepcionDatosInvalidos{
        //Se llama al metodo a testear con un dni correcto, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCliente.dniValido("45349054");
    }

    @Test
    public void testDniValidoVacio() throws ExcepcionDatosInvalidos{
        //Se llama al metodo a testear poniendo el campo vacio, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCliente.dniValido(""));
    }

    @Test
    public void testDniValidoNumeroInvalido() throws ExcepcionDatosInvalidos{
        //Se llama al metodo a testear poniendo numeros fuera del rango esperado, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCliente.dniValido("45"));
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCliente.dniValido("45349054000"));
    }
}
