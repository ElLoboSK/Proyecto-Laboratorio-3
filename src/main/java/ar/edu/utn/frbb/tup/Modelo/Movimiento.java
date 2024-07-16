package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDate;

public class Movimiento {
    private int id;
    private int idCuentaBancaria;
    private LocalDate fechaOperacion;
    private double monto;
    private String operacion;
    
    public Movimiento(int id, int idCuentaBancaria, LocalDate fechaOperacion, double monto, String operacion) {
        this.id = id;
        this.idCuentaBancaria = idCuentaBancaria;
        this.fechaOperacion = fechaOperacion;
        this.monto = monto;
        this.operacion = operacion;
    }

    //setters y getters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdCuentaBancaria(int idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public int getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setFecha(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public LocalDate getFecha() {
        return fechaOperacion;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getOperacion() {
        return operacion;
    }
}
