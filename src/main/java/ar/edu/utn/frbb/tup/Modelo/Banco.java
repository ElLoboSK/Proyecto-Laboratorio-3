package ar.edu.utn.frbb.tup.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    //clase para guardar los objetos clientes y cuentas bancarias y tener acceso a estos desde cualquier clase
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    //setters y getters
    public static void setClientes(List<Cliente> clientes) {
        Banco.clientes = clientes;
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void setCuentasBancarias(List<CuentaBancaria> cuentasBancarias) {
        Banco.cuentasBancarias = cuentasBancarias;
    }

    public static List<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }
}
