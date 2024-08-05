package ar.edu.utn.frbb.tup.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private static List<Cliente> clientes=new ArrayList<Cliente>();

    //setters y getters
    public static void setClientes(List<Cliente> clientes) {
        Banco.clientes = clientes;
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }
}
