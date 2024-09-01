package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;

@Component
public class ServicioCliente {
    private DatosCliente datosCliente;
    private DatosCuentaBancaria datosCuentaBancaria;
    private DatosMovimiento datosMovimiento;

    public ServicioCliente(DatosCliente datosCliente, DatosCuentaBancaria datosCuentaBancaria, DatosMovimiento datosMovimiento){
        this.datosCliente=datosCliente;
        this.datosCuentaBancaria=datosCuentaBancaria;
        this.datosMovimiento=datosMovimiento;
    }

    public Cliente crearCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteYaExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Long.parseLong(dniString);
        //Se verifica que no exista un cliente con el mismo DNI. Si existe, se lanza una excepcion.
        if (datosCliente.buscarClienteDni(dni)!=null) {
            throw new ExcepcionClienteYaExiste("Ya existe un cliente con el mismo DNI");
        }

        //Se busca entre los clientes registrados cual es el id mayor y se le suma 1 para obtener el id del nuevo cliente.
        List<Cliente> clientes=datosCliente.listarClientes();
        int id = 0;
        for (int i=0;i<clientes.size();i++){
            id = clientes.get(i).getId()+1;
        }

        //Se crea el cliente y se lo agrega a la base de datos.
        Cliente cliente=new Cliente(id, nombre, apellido, dni, telefono);
        datosCliente.agregarCliente(cliente);
        
        //Se retorna el cliente creado.
        return cliente;
    }

    public Cliente obtenerCliente(String dniString) throws ExcepcionClienteNoExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Long.parseLong(dniString);
        //Se verifica que exista un cliente con el DNI ingresado. Si no existe, se lanza una excepcion.
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        //Se retorna el cliente encontrado.
        return cliente;
    }

    public List<Cliente> listarClientes() throws ExcepcionNoHayClientes{
        //Se obtienen todos los clientes registrados.
        List<Cliente> clientes=datosCliente.listarClientes();
        
        //Se verifica que existan clientes registrados. Si no existen, se lanza una excepcion.
        if (clientes.size()==0){
            throw new ExcepcionNoHayClientes("No hay clientes registrados");
        }

        //Se retorna la lista de clientes.
        return clientes;
    }

    public Cliente modificarCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteNoExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Long.parseLong(dniString);
        //Se verifica que exista un cliente con el DNI ingresado. Si no existe, se lanza una excepcion.
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }
        
        //Se observa cual, o cuales, de los datos se van a modificar del cliente en base a si los campos estan vacios o no.
        if (!nombre.isEmpty()) {
            cliente.setNombre(nombre);
        }
        if (!apellido.isEmpty()) {
            cliente.setApellido(apellido);
        }
        if (!telefono.isEmpty()) {
            cliente.setTelefono(telefono);
        }

        //Se retorna el cliente modificado.
        return cliente;
    }

    public Cliente eliminarCliente(String dniString) throws ExcepcionClienteNoExiste, ExcepcionClienteTienePrestamo, ExcepcionClienteTieneSaldo{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Long.parseLong(dniString);
        //Se verifica que exista un cliente con el DNI ingresado. Si no existe, se lanza una excepcion.
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        //Se verifica que el cliente no tenga saldo en alguna cuenta bancaria. Si tiene saldo, se lanza una excepcion.
        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        for (int i=0;i<cuentasBancariasCliente.size();i++){
            if (cuentasBancariasCliente.get(i).getSaldo()>0) {
                throw new ExcepcionClienteTieneSaldo("El cliente aun tiene saldo en una cuenta bancaria");
            }
        }

        //Se verifica que el cliente no tenga prestamos activos. Si tiene prestamos activos, se lanza una excepcion.
        List<Prestamo> prestamosCliente=cliente.getPrestamos();
        if (prestamosCliente.size()!=0) {
            throw new ExcepcionClienteTienePrestamo("El cliente aun tiene un prestamo activo");
        }

        //Se eliminan las cuentas bancarias del cliente, y movimientos de las respectivas.
        for (int i=0;i<cuentasBancariasCliente.size();i++){
            List<Movimiento> movimientosCuentaBancaria=cuentasBancariasCliente.get(i).getMovimientos();
            for (int j=0;j<movimientosCuentaBancaria.size();j++){
                datosMovimiento.eliminarMovimiento(movimientosCuentaBancaria.get(j));
            }
            datosCuentaBancaria.eliminarCuentaBancaria(cuentasBancariasCliente.get(i));
        }

        //Se elimina el cliente de la base de datos.
        datosCliente.eliminarCliente(cliente);
        
        //Se retorna el cliente eliminado.
        return cliente;
    }
}
