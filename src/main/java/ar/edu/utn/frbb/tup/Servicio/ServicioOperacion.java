package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;
import java.time.LocalDate;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;

public class ServicioOperacion {
    public static String depositar(String montoString, String idCuentaBancariaString) {
        if (ValidacionesEntradas.doublePositivoValido(montoString) && ValidacionesEntradas.intPositivoValido(idCuentaBancariaString)) {
            double monto=Double.parseDouble(montoString);
            int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
            
            CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
            if (cuentaBancaria!=null) {
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
                
                return "Deposito realizado";
            }else{
                return "Error: la cuenta bancaria no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static String retirar(String montoString, String idCuentaBancariaString){
        if (ValidacionesEntradas.doublePositivoValido(montoString) && ValidacionesEntradas.intPositivoValido(idCuentaBancariaString)) {
            double monto=Double.parseDouble(montoString);
            int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
    
            CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
            if (cuentaBancaria!=null) {
                if (cuentaBancaria.getSaldo()>=monto) {
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
    
                    return "Retiro realizado";
                }else{
                    return "Error: saldo insuficiente";
                }
            }else{
                return "Error: la cuenta bancaria no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static String transferir(String montoString, String idCuentaBancariaOrigenString, String idCuentaBancariaDestinoString){
        if (ValidacionesEntradas.doublePositivoValido(montoString) && ValidacionesEntradas.intPositivoValido(idCuentaBancariaOrigenString) && ValidacionesEntradas.intPositivoValido(idCuentaBancariaDestinoString) && Integer.parseInt(idCuentaBancariaOrigenString)!=Integer.parseInt(idCuentaBancariaDestinoString)) {
            double monto=Double.parseDouble(montoString);
            int idCuentaBancariaOrigen=Integer.parseInt(idCuentaBancariaOrigenString);
            int idCuentaBancariaDestino=Integer.parseInt(idCuentaBancariaDestinoString);
            
            CuentaBancaria cuentaBancariaOrigen=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaOrigen);
            if (cuentaBancariaOrigen!=null) {
                CuentaBancaria cuentaBancariaDestino=DatosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaDestino);
                if (cuentaBancariaDestino!=null) {
                    if (cuentaBancariaOrigen.getMoneda().equals(cuentaBancariaDestino.getMoneda())) {
                        if (cuentaBancariaOrigen.getSaldo()>=monto) {
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
                    
                            return "Transferencia realizada";
                        }else{
                            return "Error: saldo insuficiente";
                        }
                    }else{
                        return "Error: las cuentas bancarias no tienen la misma moneda";
                    }
                }
            }
            return "Error: una de las cuentas bancarias no existe";
        }else{
            return "Error: datos invalidos";
        }
    }
}