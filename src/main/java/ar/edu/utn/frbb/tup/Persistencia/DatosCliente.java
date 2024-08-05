package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Cliente;

public class DatosCliente {
    private static List<Cliente> clientes=new ArrayList<Cliente>();

    public static Cliente buscarClienteId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId()==id) {
                return cliente;
            }
        }
        return null;
    }

    public static Cliente buscarClienteDni(long dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni()==dni) {
                return cliente;
            }
        }
        return null;
    }

    public static void setClientes(List<Cliente> clientesActualizados) {
        clientes=clientesActualizados;
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }
}
