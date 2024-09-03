package ar.edu.utn.frbb.tup.Controlador.Validacion.DatosCuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class TestValidacionMonedaValido {
    
    private ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validacionDatosCuentaBancaria=new ValidacionDatosCuentaBancaria();
    }
    
    @Test
    public void testMonedaValidoExitoso() throws ExcepcionDatosInvalidos{
        //Se llama al metodo a testear con tipos de moneda correctas, si no se lanza una excepcion, el test es exitoso.
        validacionDatosCuentaBancaria.monedaValido("dolares");
        validacionDatosCuentaBancaria.monedaValido("pesos");
    }

    @Test
    public void testMonedaValidoInvalido() throws ExcepcionDatosInvalidos{
        //Se llama al metodo a testear con un tipo de moneda no esperada, si se lanza una excepcion, el test es exitoso.
        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCuentaBancaria.monedaValido("euros"));
    }
}
