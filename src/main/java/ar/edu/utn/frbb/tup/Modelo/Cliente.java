package ar.edu.utn.frbb.tup.Modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Persona {
    private List<CuentaBancaria> cuentasBancarias;
    private List<Prestamo> prestamos;
    private int id;

    public Cliente(String nombre, String apellido, long dni, String telefono, int id) {
        super(nombre, apellido, dni, telefono);
        this.id = id;
        this.cuentasBancarias = new ArrayList<CuentaBancaria>();
        this.prestamos = new ArrayList<Prestamo>();
    }

    //setters y getters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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