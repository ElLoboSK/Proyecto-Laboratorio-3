package ar.edu.utn.frbb.tup.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();
    static List<Prestamo> prestamos=new ArrayList<Prestamo>();

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

    public static void setPrestamos(List<Prestamo> prestamos) {
        Banco.prestamos = prestamos;
    }

    public static List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
