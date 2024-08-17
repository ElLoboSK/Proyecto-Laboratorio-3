package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionNoHayClientes;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;

public class ServicioCliente {
    public static Cliente crearCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteYaExiste, ExcepcionDatosInvalidos{
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);
            if (DatosCliente.buscarClienteDni(dni)!=null) {
                throw new ExcepcionClienteYaExiste("Ya existe un cliente con el mismo DNI");
            }

            List<Cliente> clientes=DatosCliente.getClientes();
            int id = 0;
            for (int i=0;i<clientes.size();i++){
                id = clientes.get(i).getId()+1;
            }

            Cliente cliente=new Cliente(nombre, apellido, dni, telefono, id);
            clientes.add(cliente);
            DatosCliente.setClientes(clientes);
            
            return cliente;
        }else{
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
    }

    public static Cliente obtenerCliente(String dniString) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos{
        if (ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);
    
            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                return cliente;
            }else{
                throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
            }
        }else{
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
    }

    public static List<Cliente> listarClientes() throws ExcepcionNoHayClientes{
        List<Cliente> clientes=DatosCliente.getClientes();
        if (clientes.size()!=0){
            return clientes;
        }else{
            throw new ExcepcionNoHayClientes("No hay clientes registrados");
        }
    }

    public static Cliente modificarCliente(String dniString, String nombre, String apellido, String telefono) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos{
        if (ValidacionesCliente.dniValido(dniString) && (!nombre.isEmpty() || !apellido.isEmpty() || !telefono.isEmpty())) {
            long dni=Long.parseLong(dniString);
            
            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
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
            }else{
                throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
            }
        }else{
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
    }

    public static Cliente eliminarCliente(String dniString) throws ExcepcionClienteNoExiste, ExcepcionDatosInvalidos, ExcepcionClienteTienePrestamo, ExcepcionClienteTieneSaldo{
        if (ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);

            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++){
                    if (cuentasBancariasCliente.get(i).getSaldo()>0) {
                        throw new ExcepcionClienteTieneSaldo("El cliente aun tiene saldo en una cuenta bancaria");
                    }
                }
                List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuentasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++){
                    cuentasBancarias.remove(cuentasBancariasCliente.get(i));
                }
                DatosCuentaBancaria.setCuentasBancarias(cuentasBancarias);

                List<Prestamo> prestamosCliente=cliente.getPrestamos();
                if (prestamosCliente.size()!=0) {
                    throw new ExcepcionClienteTienePrestamo("El cliente aun tiene un prestamo activo");
                }

                List<Cliente> clientes=DatosCliente.getClientes();
                clientes.remove(cliente);
                DatosCliente.setClientes(clientes);
                
                return cliente;
            }else{
                throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
            }
        }else{
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
    }
}
