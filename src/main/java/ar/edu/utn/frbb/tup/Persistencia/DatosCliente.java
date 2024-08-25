package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Cliente;

@Repository
public class DatosCliente {
    private List<Cliente> clientes=new ArrayList<Cliente>();

    public Cliente buscarClienteId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId()==id) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarClienteDni(long dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni()==dni) {
                return cliente;
            }
        }
        return null;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes=clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
