package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
    private int id;
    private int idCliente;
    private LocalDate fechaApertura;
    private double saldo;
    private String cbu;
    private String tipoCuenta;
    private String moneda;
    private List<Movimiento> movimientos;

    public CuentaBancaria(int id, int idCliente, LocalDate fechaApertura, double saldo, String cbu, String tipoCuenta, String moneda) {
        this.id = id;
        this.idCliente = idCliente;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.cbu = cbu;
        this.tipoCuenta = tipoCuenta;
        this.moneda = moneda;
        this.movimientos = new ArrayList<Movimiento>();
    }

    //setters y getters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCbu() {
        return cbu;
    }
    
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
}
