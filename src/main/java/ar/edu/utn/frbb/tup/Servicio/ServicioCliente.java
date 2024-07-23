package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;

public class ServicioCliente {
    //se crean 2 listas para luego llamarlas desde la clase banco y actualizarlas si se modifican
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    //Metodos principales
    public static String crearCliente(String nombre, String apellido, String dniString, String telefono) {
        clientes=Banco.getClientes();

        if (!nombre.isEmpty() && !apellido.isEmpty() && ValidacionesCliente.dniValido(dniString)) {
            long dni=Long.parseLong(dniString);
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni()==dni){
                    return "Error: el cliente ya existe";
                }
            }
            int id = 0;
            for (int i=0;i<clientes.size();i++){
                id = clientes.get(i).getId()+1;
            }

            Cliente cliente=new Cliente(nombre, apellido, dni, telefono, id);
            clientes.add(cliente);
            
            Banco.setClientes(clientes);
            
            return "Cliente creado";
        }else{
            return "Error: datos invalidos";
        }
    }

    public static Object mostrarCliente(String dniString){
        clientes=Banco.getClientes();

        if (clientes.size()!=0){
            if (!ValidacionesCliente.dniValido(dniString)) {
                return "Error: DNI invalido";
            }
            long dni=Long.parseLong(dniString);
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni()==dni){
                    return clientes.get(i);
                }
            }
        }else{
            return "Error: no hay clientes";
        }
        return "Error: el cliente no existe";
    }

    public static Object listarClientes() {
        clientes=Banco.getClientes();
        
        if (clientes.size()!=0){
            clientes=Banco.getClientes();
            return clientes;
        }else{
            return "Error: no hay clientes";
        }
    }

    public static String modificarCliente(String dniString, String nombre, String apellido, String telefono) {
        clientes=Banco.getClientes();
        
        if (clientes.size()!=0){
            if (!ValidacionesCliente.dniValido(dniString)) {
                return "Error: DNI invalido";
            }
            long dni=Long.parseLong(dniString);
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni()==dni){
                    if (nombre==null && apellido==null && telefono==null) {
                        return "Error: datos invalidos";
                    }
                    if (nombre!=null) {
                        clientes.get(i).setNombre(nombre);
                    }
                    if (apellido!=null) {
                        clientes.get(i).setApellido(apellido);
                    }
                    if (telefono!=null) {
                        clientes.get(i).setTelefono(telefono);
                    }
                    Banco.setClientes(clientes);
                    return "Cliente modificado";
                }
            }
        }else{
            return "Error: no hay clientes";
        }
        return "Error: el cliente no existe";
    }

    public static String eliminarCliente(String dniString) {
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();
        
        if (clientes.size()!=0){
            if (!ValidacionesCliente.dniValido(dniString)) {
                return "Error: DNI invalido";
            }
            long dni=Long.parseLong(dniString);
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni()==dni){
                    if (clientes.get(i).getCuentasBancarias().size()!=0) {
                        for (int j=0;j<clientes.get(i).getCuentasBancarias().size();j++){
                            if (clientes.get(i).getCuentasBancarias().get(j).getSaldo()>0) {
                                return "Error: el cliente tiene saldo en una cuenta bancaria";
                            }
                            else{
                                for(int k=0;k<cuentasBancarias.size();k++){
                                    if (cuentasBancarias.get(k).getId()==clientes.get(i).getCuentasBancarias().get(j).getId()) {
                                        cuentasBancarias.remove(k);
                                    }
                                }
                            }
                        }
                    }
                    Banco.setCuentasBancarias(cuentasBancarias);
                    clientes.remove(i);
                    Banco.setClientes(clientes);
                    return "Cliente eliminado";
                }
            }
        }else{
            return "Error: no hay clientes";
        }
        return "Error: el cliente no existe";
    }
}
