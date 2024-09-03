package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosCuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestValidacionDatosCrearCuentaBancaria {

    private ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosCuentaBancaria=new ValidacionDatosCuentaBancaria();
    }
    
    @Test
    public void testDatosCrearCuentaBancariaExitoso() throws ExcepcionDatosInvalidos{
        //Se cren los datos de entrada para el test y se agregan a un diccionario.
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("tipoCuenta", "caja de ahorro");
        datos.put("moneda", "dolares");

        //Se llama al metodo a testear, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCuentaBancaria.datosCrearCuentaBancaria(datos);
    }

    @Test
    public void testDatosCrearCuentaBancariaFaltanCampos() throws ExcepcionDatosInvalidos{
        //Se crea un diccionario sin campos.
        Map<String, String> datos=new HashMap<>();

        //Se llama al metodo a testear, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCuentaBancaria.datosCrearCuentaBancaria(datos));
    }
}
