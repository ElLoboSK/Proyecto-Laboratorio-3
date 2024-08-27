package ar.edu.utn.frbb.tup.Modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Persona {
    private List<CuentaBancaria> cuentasBancarias;
    private List<Prestamo> prestamos;

    public Cliente(int id, String nombre, String apellido, long dni, String telefono) {
        super(id, nombre, apellido, dni, telefono);
        this.cuentasBancarias = new ArrayList<CuentaBancaria>();
        this.prestamos = new ArrayList<Prestamo>();
    }

    //setters y getters
    public List<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<CuentaBancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}