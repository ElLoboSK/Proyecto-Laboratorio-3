package ar.edu.utn.frbb.tup.Controlador.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Controlador.ControladorCuentaBancaria;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatos;
import ar.edu.utn.frbb.tup.Controlador.Validaciones.ValidacionDatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class testControladorListarCuentasBancarias {
    
    @Mock
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @Mock
    private ValidacionDatosCuentaBancaria validacionDatosCuentaBancaria;

    @Mock
    private ValidacionDatos ValidacionDatos;

    @InjectMocks
    private ControladorCuentaBancaria controladorCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controladorCuentaBancaria=new ControladorCuentaBancaria(servicioCuentaBancaria, validacionDatosCuentaBancaria, ValidacionDatos);
    }

    @Test
    public void testListarCuentasBancariasExitoso() throws ExcepcionNoHayCuentasBancarias{
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
        CuentaBancaria cuentaBancaria2=new CuentaBancaria(1, 0, LocalDate.now(), 0, "123456", "cuenta corriente", "dolares");

        List<CuentaBancaria> cuentasBancarias=new ArrayList<>();
        cuentasBancarias.add(cuentaBancaria);
        cuentasBancarias.add(cuentaBancaria2);

        when(servicioCuentaBancaria.listarCuentasBancarias()).thenReturn(cuentasBancarias);

        assertEquals("200 OK", String.valueOf(controladorCuentaBancaria.listarCuentasBancarias().getStatusCode()));
        assertEquals(cuentasBancarias, controladorCuentaBancaria.listarCuentasBancarias().getBody());
    }

    @Test
    public void testListarCuentasBancariasExcepcionNoHayCuentasBancarias() throws ExcepcionNoHayCuentasBancarias{
        doThrow(ExcepcionNoHayCuentasBancarias.class).when(servicioCuentaBancaria).listarCuentasBancarias();

        assertThrows(ExcepcionNoHayCuentasBancarias.class, () -> controladorCuentaBancaria.listarCuentasBancarias());
    }
}
