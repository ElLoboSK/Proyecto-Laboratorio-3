package ar.edu.utn.frbb.tup.Servicio.Prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioScoreCrediticio;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioSolicitarPrestamo {
    
    @Mock
    private DatosPrestamo datosPrestamo;
    
    @Mock
    private DatosCliente datosCliente;

    @Mock
    private ServicioScoreCrediticio servicioScoreCrediticio;

    @InjectMocks
    private ServicioPrestamo servicioPrestamo;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        servicioPrestamo=new ServicioPrestamo(datosPrestamo, datosCliente, servicioScoreCrediticio);
    }
    
    @Test
    public void testSolicitarPrestamoExitosoAceptado() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crea un cliente y una cuenta bancaria para asignarle al mismo.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
    
        List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();
        cuentasBancarias.add(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancarias);

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);
        
        //Se simula el comportamiento del servicio de score crediticio.
        when(servicioScoreCrediticio.scoreCrediticio(cliente.getDni())).thenReturn(true);

        //Se realiza la solicitud del prestamo.
        Map<String, Object> resultado=servicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "dolares");
        
        //Se verifica que el prestamo se haya realizado correctamente.
        assertEquals("Aprobado", resultado.get("estado"));
        assertEquals(12000, cuentaBancaria.getSaldo());
        assertNotNull(resultado);
    }
    
    @Test
    public void testSolicitarPrestamoExitosoRechazado() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se crea un cliente y una cuenta bancaria para asignarle al mismo.
        Cliente cliente=new Cliente(0, "Joaco", "Widmer", 44741717, "2932502274");
        CuentaBancaria cuentaBancaria=new CuentaBancaria(0, 0, LocalDate.now(), 0, "123456", "caja de ahorro", "dolares");
    
        List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();
        cuentasBancarias.add(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancarias);

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);

        //Se simula el comportamiento del servicio de score crediticio.
        when(servicioScoreCrediticio.scoreCrediticio(cliente.getDni())).thenReturn(false);
        
        //Se realiza la solicitud del prestamo.
        Map<String, Object> resultado=servicioPrestamo.solicitarPrestamo("44741717", "10", "12000", "dolares");
        
        //Se verifica que el prestamo se haya realizado correctamente.
        assertEquals("Rechazado", resultado.get("estado"));
        assertNotNull(resultado);
    }

    @Test
    public void testSolicitarPrestamoClienteNoExiste() throws ExcepcionClienteNoExiste{
        //Se llama al metodo y se verifica que se lance la excepcion por la falta de cliente.
        assertThrows(ExcepcionClienteNoExiste.class, () -> servicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "dolares"));
    }

    @Test
    public void testSolicitarPrestamoCuentaBancariaNoExiste() throws ExcepcionCuentaBancariaMonedaNoExiste, ExcepcionClienteYaExiste{
        //Se crea un cliente sin cuenta bancaria.
        Cliente cliente=new Cliente(0, "Galo", "Santopietro", 45349054, "2932502274");

        //Se simula el comportamiento de la base de datos.
        when(datosCliente.buscarClienteDni(cliente.getDni())).thenReturn(cliente);

        //Se llama al metodo y se verifica que se lance la excepcion por la falta de cuenta bancaria.
        assertThrows(ExcepcionCuentaBancariaMonedaNoExiste.class, () -> servicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "dolares"));
    }
}
