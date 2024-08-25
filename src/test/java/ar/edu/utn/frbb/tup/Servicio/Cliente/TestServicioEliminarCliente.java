package ar.edu.utn.frbb.tup.Servicio.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.ServicioOperacion;
import ar.edu.utn.frbb.tup.Servicio.ServicioPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServicioEliminarCliente {
    
    @Mock
    private DatosCuentaBancaria datosCuentaBancaria;

    @Mock
    private DatosPrestamo datosPrestamo;

    @Mock
    private DatosCliente datosCliente;

    @InjectMocks
    private ServicioCliente servicioCliente;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    //    DatosCliente.setClientes(new ArrayList<>());
    //    DatosCuentaBancaria.setCuentasBancarias(new ArrayList<>());
    //    DatosPrestamo.setPrestamos(new ArrayList<>());
    }

    @Test
    public void testEliminarClienteExitoso() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
    //    Cliente clienteCreado=ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");

    //    Cliente clienteEliminado=ServicioCliente.eliminarCliente("45349054");
        
    //    assertEquals(clienteCreado, clienteEliminado);
    //    assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioCliente.obtenerCliente("45349054"));
    }

    @Test
    public void testEliminarClienteNoExiste() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
    //    assertThrows(ExcepcionClienteNoExiste.class, () -> ServicioCliente.eliminarCliente("45349054"));
    }

    @Test
    public void testEliminarClienteDatosInvalidos() throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo{
    //    assertThrows(ExcepcionDatosInvalidos.class, () -> ServicioCliente.eliminarCliente(""));
    }

    @Test
    public void testEliminarClienteConSaldo() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteTieneSaldo, ExcepcionClienteTienePrestamo, ExcepcionCuentaBancariaYaExiste, ExcepcionCuentaBancariaNoExiste{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
    //    ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
    //    ServicioOperacion.depositar("12000", "0");

    //    assertThrows(ExcepcionClienteTieneSaldo.class, () -> ServicioCliente.eliminarCliente("45349054"));
    }

    @Test
    public void testEliminarClienteConPrestamo() throws ExcepcionClienteNoExiste, ExcepcionClienteYaExiste, ExcepcionDatosInvalidos, ExcepcionCuentaBancariaYaExiste,ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaMonedaNoExiste, ExcepcionSaldoInsuficiente{
    //    ServicioCliente.crearCliente("45349054", "Galo", "Santopietro", "2932502274");
    //    ServicioCuentaBancaria.crearCuentaBancaria("45349054", "Caja de ahorro", "Dolares");
    //    ServicioPrestamo.solicitarPrestamo("45349054", "10", "12000", "Dolares");
    //    ServicioOperacion.retirar("12000", "0");

    //    assertThrows(ExcepcionClienteTienePrestamo.class, () -> ServicioCliente.eliminarCliente("45349054"));
    }
}
