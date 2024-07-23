package ar.edu.utn.frbb.tup.Modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Persona {
    private List<CuentaBancaria> cuentasBancarias;
    private int id;

    public Cliente(String nombre, String apellido, long dni, String telefono, int id) {
        super(nombre, apellido, dni, telefono);
        this.id = id;
        this.cuentasBancarias = new ArrayList<CuentaBancaria>();
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
}