package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Cliente;

@Repository
public class DatosCliente {
    private List<Cliente> clientes=new ArrayList<Cliente>();

    public Cliente buscarClienteId(int id) {
        //Se busca el cliente con el ID entre los clientes registrados y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (Cliente cliente : clientes) {
            if (cliente.getId()==id) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarClienteDni(long dni) {
        //Se busca el cliente con el DNI entre los clientes registrados y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (Cliente cliente : clientes) {
            if (cliente.getDni()==dni) {
                return cliente;
            }
        }
        return null;
    }

    public void agregarCliente(Cliente cliente) {
        //Se agrega el cliente a la lista de clientes.
        clientes.add(cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        //Se elimina el cliente de la lista de clientes.
        clientes.remove(cliente);
    }

    public List<Cliente> listarClientes() {
        //Se devuelve la lista entera de clientes.
        return clientes;
    }
}
