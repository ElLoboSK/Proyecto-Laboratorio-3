package ar.edu.utn.frbb.tup.Modelo;

public class Prestamo {
    private int id;
    private int idCliente;
    private double monto;
    private int plazoMeses;
    private int pagosRealizados;
    private double saldoRestante;

    public Prestamo(int id, int idCliente, double monto, int plazoMeses, int pagosRealizados, double saldoRestante) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.pagosRealizados = pagosRealizados;
        this.saldoRestante = saldoRestante;
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

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPagosRealizados(int pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }

    public int getPagosRealizados() {
        return pagosRealizados;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }
}
