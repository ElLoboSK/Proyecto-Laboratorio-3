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
public class TestValidacionDatosOperacionTransferencia {
    
    private ValidacionDatosOperacion validacionDatosOperacion;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosOperacion=new ValidacionDatosOperacion();
    }

    @Test
    public void testDatosOperacionTransferenciaExitoso() throws ExcepcionDatosInvalidos{
        //Se crean los datos de entrada para el test y se agregan a un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("monto", "12000");
        datos.put("idCuentaBancariaOrigen", "0");
        datos.put("idCuentaBancariaDestino", "1");

        //Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosOperacion.datosOperacionTransferencia(datos);
    }

    @Test
    public void testDatosOperacionTransferenciaFaltanCampos() throws ExcepcionDatosInvalidos{
        //Se crea un diccionario sin campos.
        Map<String, String> datos=new HashMap<>();

        //Se llama al metodo a testear, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosOperacion.datosOperacionTransferencia(datos));
    }
}
