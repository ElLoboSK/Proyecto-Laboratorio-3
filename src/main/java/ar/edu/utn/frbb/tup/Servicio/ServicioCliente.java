package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;

public class ServicioCliente {
    public static String crearCliente(String dniString, String nombre, String apellido, String telefono) {
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);
            if (DatosCliente.buscarClienteDni(dni)!=null) {
                return "Error: el cliente ya existe";
            }

            List<Cliente> clientes=DatosCliente.getClientes();
            int id = 0;
            for (int i=0;i<clientes.size();i++){
                id = clientes.get(i).getId()+1;
            }

            Cliente cliente=new Cliente(nombre, apellido, dni, telefono, id);
            clientes.add(cliente);
            DatosCliente.setClientes(clientes);
            
            return "Cliente creado";
        }else{
            return "Error: datos invalidos";
        }
    }

    public static Object mostrarCliente(String dniString){
        if (ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);
    
            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                return cliente;
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static Object listarClientes() {
        List<Cliente> clientes=DatosCliente.getClientes();
        if (clientes.size()!=0){
            return clientes;
        }else{
            return "Error: no hay clientes";
        }
    }

    public static String modificarCliente(String dniString, String nombre, String apellido, String telefono) {
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
                return "Cliente modificado";
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static String eliminarCliente(String dniString) {
        if (ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);

            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++){
                    if (cuentasBancariasCliente.get(i).getSaldo()>0) {
                        return "Error: el cliente tiene saldo en una cuenta bancaria";   
                    }
                }
                List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuestasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++){
                    cuentasBancarias.remove(cuentasBancariasCliente.get(i));
                }
                DatosCuentaBancaria.setCuentasBancarias(cuentasBancarias);

                List<Prestamo> prestamosCliente=cliente.getPrestamos();
                List<Prestamo> prestamos=DatosPrestamo.getPrestamos();
                for(int i=0;i<prestamosCliente.size();i++) {
                    prestamos.remove(prestamosCliente.get(i));
                }
                DatosPrestamo.setPrestamos(prestamos);

                List<Cliente> clientes=DatosCliente.getClientes();
                clientes.remove(cliente);
                DatosCliente.setClientes(clientes);
                return "Cliente eliminado";
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }
}
