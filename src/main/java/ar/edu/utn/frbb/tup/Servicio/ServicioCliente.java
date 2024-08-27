package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

@Component
public class ServicioCliente {
    private DatosCliente datosCliente;
    private DatosCuentaBancaria datosCuentaBancaria;

    public ServicioCliente(DatosCliente datosCliente, DatosCuentaBancaria datosCuentaBancaria){
        this.datosCliente=datosCliente;
        this.datosCuentaBancaria=datosCuentaBancaria;
    }

    public Cliente crearCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteYaExiste{
        long dni=Long.parseLong(dniString);
        if (datosCliente.buscarClienteDni(dni)!=null) {
            throw new ExcepcionClienteYaExiste("Ya existe un cliente con el mismo DNI");
        }

        List<Cliente> clientes=datosCliente.listarClientes();
        int id = 0;
        for (int i=0;i<clientes.size();i++){
            id = clientes.get(i).getId()+1;
        }

        Cliente cliente=new Cliente(nombre, apellido, dni, telefono, id);
        datosCliente.agregarCliente(cliente);
        
        return cliente;
    }

    public Cliente obtenerCliente(String dniString) throws ExcepcionClienteNoExiste{
        long dni=Long.parseLong(dniString);
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        return cliente;
    }

    public List<Cliente> listarClientes() throws ExcepcionNoHayClientes{
        List<Cliente> clientes=datosCliente.listarClientes();
        
        if (clientes.size()==0){
            throw new ExcepcionNoHayClientes("No hay clientes registrados");
        }

        return clientes;
    }

    public Cliente modificarCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteNoExiste{
        long dni=Long.parseLong(dniString);
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }
        
        if (!nombre.isEmpty()) {
            cliente.setNombre(nombre);
        }
        if (!apellido.isEmpty()) {
            cliente.setApellido(apellido);
        }
        if (!telefono.isEmpty()) {
            cliente.setTelefono(telefono);
        }

        return cliente;
    }

    public Cliente eliminarCliente(String dniString) throws ExcepcionClienteNoExiste, ExcepcionClienteTienePrestamo, ExcepcionClienteTieneSaldo{
        long dni=Long.parseLong(dniString);
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        for (int i=0;i<cuentasBancariasCliente.size();i++){
            if (cuentasBancariasCliente.get(i).getSaldo()>0) {
                throw new ExcepcionClienteTieneSaldo("El cliente aun tiene saldo en una cuenta bancaria");
            }
        }

        List<Prestamo> prestamosCliente=cliente.getPrestamos();
        if (prestamosCliente.size()!=0) {
            throw new ExcepcionClienteTienePrestamo("El cliente aun tiene un prestamo activo");
        }

        for (int i=0;i<cuentasBancariasCliente.size();i++){
            datosCuentaBancaria.eliminarCuentaBancaria(cuentasBancariasCliente.get(i));
        }
        
        datosCliente.eliminarCliente(cliente);
        
        return cliente;
    }
}
