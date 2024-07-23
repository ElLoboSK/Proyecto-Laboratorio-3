package ar.edu.utn.frbb.tup.Modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
    private int idCliente;
    private LocalDate fechaApertura;
    private double saldo;
    private String cbu;
    private String tipoCuenta;
    private List<Movimiento> movimientos;
    private int id;

    public CuentaBancaria(int id, int idCliente, LocalDate fechaApertura, double saldo, String cbu, String tipoCuenta) {
        this.id = id;
        this.idCliente = idCliente;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.cbu = cbu;
        this.tipoCuenta = tipoCuenta;
        this.movimientos = new ArrayList<Movimiento>();
    }

    public void mostrarDatos(){
        System.out.println("\nId: "+this.id);
        System.out.println("Id Cliente: "+this.idCliente);
        System.out.println("Fecha de apertura: "+this.fechaApertura);
        System.out.println("Saldo: "+this.saldo);
        System.out.println("CBU: "+this.cbu);
        System.out.println("Tipo de cuenta: "+this.tipoCuenta);
        System.out.println("Movimientos: ");
        if (this.movimientos.size()==0) {
            System.out.println("No hay movimientos");
        }else{
            for (int i=0;i<this.movimientos.size();i++){
                System.out.println("ID: "+this.movimientos.get(i).getId()+", Fecha: "+this.movimientos.get(i).getFecha()+", Monto: "+this.movimientos.get(i).getMonto()+", Operacion: "+this.movimientos.get(i).getOperacion());
            }
        }
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

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
}
