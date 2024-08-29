package ar.edu.utn.frbb.tup.Controlador.Validacion.ValidacionDatosCuentaBancaria;

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
        Map<String, String> datos=new HashMap<>();
        datos.put("dni", "45349054");
        datos.put("tipoCuenta", "caja de ahorro");
        datos.put("moneda", "dolares");

        validacionDatosCuentaBancaria.datosCrearCuentaBancaria(datos);
    }

    @Test
    public void testDatosCrearCuentaBancariaFaltanCampos() throws ExcepcionDatosInvalidos{
        Map<String, String> datos=new HashMap<>();

        assertThrows(ExcepcionDatosInvalidos.class, () -> validacionDatosCuentaBancaria.datosCrearCuentaBancaria(datos));
    }
}
