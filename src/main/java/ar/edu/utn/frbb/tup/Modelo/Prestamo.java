package ar.edu.utn.frbb.tup.Modelo;

public class Prestamo {
    private double monto;
    private int plazoMeses;
    private int pagosRealizados;
    private double saldoRestante;

    public Prestamo(double monto, int plazoMeses, int pagosRealizados, double saldoRestante) {
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.pagosRealizados = pagosRealizados;
        this.saldoRestante = saldoRestante;
    }

    //setters y getters
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
