package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;

public class ServicioOperacion {
    public static Movimiento depositar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos{
        if (!ValidacionesEntradas.doublePositivoValido(montoString) || !ValidacionesEntradas.intPositivoValido(idCuentaBancariaString)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
        
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);

        CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        List<Movimiento> movimientos=DatosMovimiento.getMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Deposito");
        
        movimientos.add(movimiento);
        DatosMovimiento.setMovimientos(movimientos);

        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() + monto;
        cuentaBancaria.setSaldo(saldoFinal);
        
        return movimiento;
    }

    public static Movimiento retirar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente{
        if (!ValidacionesEntradas.doublePositivoValido(montoString) || !ValidacionesEntradas.intPositivoValido(idCuentaBancariaString)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
        
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
        
        CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        if (cuentaBancaria.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar el retiro");
        }

        List<Movimiento> movimientos=DatosMovimiento.getMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Retiro");

        movimientos.add(movimiento);
        DatosMovimiento.setMovimientos(movimientos);

        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() - monto;
        cuentaBancaria.setSaldo(saldoFinal);

        return movimiento;
    }

    public static List<Movimiento> transferir(String montoString, String idCuentaBancariaOrigenString, String idCuentaBancariaDestinoString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        if (!ValidacionesEntradas.doublePositivoValido(montoString) || !ValidacionesEntradas.intPositivoValido(idCuentaBancariaOrigenString) || !ValidacionesEntradas.intPositivoValido(idCuentaBancariaDestinoString)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }

        double monto=Double.parseDouble(montoString);
        int idCuentaBancariaOrigen=Integer.parseInt(idCuentaBancariaOrigenString);
        int idCuentaBancariaDestino=Integer.parseInt(idCuentaBancariaDestinoString);

        if (idCuentaBancariaOrigen==idCuentaBancariaDestino) {
            throw new ExcepcionMismaCuentaBancaria("La cuenta bancaria de origen y destino son la misma");
        }

        CuentaBancaria cuentaBancariaOrigen=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaOrigen);
        CuentaBancaria cuentaBancariaDestino=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaDestino);
        if (cuentaBancariaOrigen==null || cuentaBancariaDestino==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        if (!cuentaBancariaOrigen.getMoneda().equals(cuentaBancariaDestino.getMoneda())) {
            throw new ExcepcionMonedaDiferente("La moneda de la cuenta bancaria origen es diferente a la de la cuenta bancaria destino");
        }
        
        if (cuentaBancariaOrigen.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar la transferencia");
        }

        List<Movimiento> movimientos=DatosMovimiento.getMovimientos();
        int idMovimientoOrigen=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoOrigen=movimientos.get(i).getId()+1;
        }
        int idMovimientoDestino=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoDestino=movimientos.get(i).getId()+2;
        }
        LocalDate fechaOperacion = LocalDate.now();

        Movimiento movimientoOrigen = new Movimiento(idMovimientoOrigen,idCuentaBancariaOrigen,fechaOperacion,monto,"Transferencia enviada");
        Movimiento movimientoDestino = new Movimiento(idMovimientoDestino,idCuentaBancariaDestino,fechaOperacion,monto,"Transferencia recibida");
        
        movimientos.add(movimientoOrigen);
        movimientos.add(movimientoDestino);
        DatosMovimiento.setMovimientos(movimientos);

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