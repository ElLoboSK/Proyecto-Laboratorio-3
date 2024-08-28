package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.time.LocalDate;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;

@Component
public class ServicioOperacion {
    private DatosMovimiento datosMovimiento;
    private DatosCuentaBancaria datosCuentaBancaria;

    public ServicioOperacion(DatosMovimiento datosMovimiento, DatosCuentaBancaria datosCuentaBancaria){
        this.datosMovimiento=datosMovimiento;
        this.datosCuentaBancaria=datosCuentaBancaria;
    }

    public Movimiento depositar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste{
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);

        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"deposito");
        
        datosMovimiento.agregarMovimiento(movimiento);

        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() + monto;
        cuentaBancaria.setSaldo(saldoFinal);
        
        return movimiento;
    }

    public Movimiento retirar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente{
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
        
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        if (cuentaBancaria.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar la operacion");
        }

        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"retiro");

        datosMovimiento.agregarMovimiento(movimiento);

        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() - monto;
        cuentaBancaria.setSaldo(saldoFinal);

        return movimiento;
    }

    public List<Movimiento> transferir(String montoString, String idCuentaBancariaOrigenString, String idCuentaBancariaDestinoString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        double monto=Double.parseDouble(montoString);
        int idCuentaBancariaOrigen=Integer.parseInt(idCuentaBancariaOrigenString);
        int idCuentaBancariaDestino=Integer.parseInt(idCuentaBancariaDestinoString);

        if (idCuentaBancariaOrigen==idCuentaBancariaDestino) {
            throw new ExcepcionMismaCuentaBancaria("La cuenta bancaria de origen y destino son la misma");
        }

        CuentaBancaria cuentaBancariaOrigen=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaOrigen);
        CuentaBancaria cuentaBancariaDestino=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaDestino);
        if (cuentaBancariaOrigen==null || cuentaBancariaDestino==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        if (!cuentaBancariaOrigen.getMoneda().equals(cuentaBancariaDestino.getMoneda())) {
            throw new ExcepcionMonedaDiferente("La moneda de la cuenta bancaria origen es diferente a la de la cuenta bancaria destino");
        }
        
        if (cuentaBancariaOrigen.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar la operacion");
        }

        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimientoOrigen=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoOrigen=movimientos.get(i).getId()+1;
        }
        int idMovimientoDestino=1;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoDestino=movimientos.get(i).getId()+2;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimientoOrigen = new Movimiento(idMovimientoOrigen,idCuentaBancariaOrigen,fechaOperacion,monto,"transferencia enviada");
        Movimiento movimientoDestino = new Movimiento(idMovimientoDestino,idCuentaBancariaDestino,fechaOperacion,monto,"transferencia recibida");
        
        datosMovimiento.agregarMovimiento(movimientoOrigen);
        datosMovimiento.agregarMovimiento(movimientoDestino);

        List<Movimiento> movimientosCuentaBancariaOrigen = cuentaBancariaOrigen.getMovimientos();
        movimientosCuentaBancariaOrigen.add(movimientoOrigen);
        cuentaBancariaOrigen.setMovimientos(movimientosCuentaBancariaOrigen);

        List<Movimiento> movimientosCuentaBancariaDestino = cuentaBancariaDestino.getMovimientos();
        movimientosCuentaBancariaDestino.add(movimientoDestino);
        cuentaBancariaDestino.setMovimientos(movimientosCuentaBancariaDestino);

        double saldoFinalOrigen = cuentaBancariaOrigen.getSaldo() - monto;
        cuentaBancariaOrigen.setSaldo(saldoFinalOrigen);

        double saldoFinalDestino = cuentaBancariaDestino.getSaldo() + monto;
        cuentaBancariaDestino.setSaldo(saldoFinalDestino);

        List<Movimiento> movimientosTransferencia = new ArrayList<Movimiento>();
        movimientosTransferencia.add(movimientoOrigen);
        movimientosTransferencia.add(movimientoDestino);

        return movimientosTransferencia;
    }
}