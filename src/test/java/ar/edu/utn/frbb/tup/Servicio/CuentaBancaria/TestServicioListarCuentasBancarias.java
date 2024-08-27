package ar.edu.utn.frbb.tup.Servicio.CuentaBancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioListarCuentasBancarias {
    
    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private DatosMovimiento datosMovimiento;

    @InjectMocks
    private ServicioCuentaBancaria servicioCuentaBancaria;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioCuentaBancaria = new ServicioCuentaBancaria(datosCuentaBancaria, datosCliente, datosMovimiento);
    }

    @Test
    public void testListarCuentasBancariasExitoso() throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste, ExcepcionCuentaBancariaNoExiste, ExcepcionClienteYaExiste, ExcepcionNoHayCuentasBancarias{
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        when(datosCliente.buscarClienteDni(45349054)).thenReturn(cliente);

        CuentaBancaria cuentaBancaria=servicioCuentaBancaria.crearCuentaBancaria("45349054", "caja de ahorro", "dolares");
        CuentaBancaria cuentaBancaria2=servicioCuentaBancaria.crearCuentaBancaria("45349054", "cuenta corriente", "dolares");

        List<CuentaBancaria> cuentasBancariasCreadas=new ArrayList<>();
        cuentasBancariasCreadas.add(cuentaBancaria);
        cuentasBancariasCreadas.add(cuentaBancaria2);

        when(datosCuentaBancaria.listarCuentasBancarias()).thenReturn(cuentasBancariasCreadas);

        List<CuentaBancaria> cuentasBancariasListadas=servicioCuentaBancaria.listarCuentasBancarias();

        assertEquals(2, cuentasBancariasListadas.size());
        assertEquals(cuentasBancariasCreadas.get(0), cuentasBancariasListadas.get(0));
        assertEquals(cuentasBancariasCreadas.get(1), cuentasBancariasListadas.get(1));
        assertNotNull(cuentasBancariasListadas);
    }

    @Test
    public void testListarCuentasBancariasNoHayCuentasBancarias() throws ExcepcionNoHayCuentasBancarias{
        assertThrows(ExcepcionNoHayCuentasBancarias.class, () -> servicioCuentaBancaria.listarCuentasBancarias());
    }
}
