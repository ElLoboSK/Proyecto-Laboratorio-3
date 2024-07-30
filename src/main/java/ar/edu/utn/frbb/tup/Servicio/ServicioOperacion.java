package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesOperacion;

public class ServicioOperacion {
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static String depositar(String montoString, String idCuentaBancariaString) {
        cuentasBancarias=Banco.getCuentasBancarias();

        if (cuentasBancarias.size()!=0) {
            if (ValidacionesOperacion.montoValido(montoString) && ValidacionesEntradas.intValido(idCuentaBancariaString) && Integer.parseInt(idCuentaBancariaString)>=0) {
                double monto=Double.parseDouble(montoString);
                int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
                for(int i=0;i<cuentasBancarias.size();i++) {
                    if (cuentasBancarias.get(i).getId()==idCuentaBancaria) {
                        int idMovimiento=0;
                        if (cuentasBancarias.get(i).getMovimientos().size()!=0) {
                            for(int j=0;j<cuentasBancarias.get(i).getMovimientos().size();j++) {
                                idMovimiento=cuentasBancarias.get(i).getMovimientos().get(i).getId()+1;
                            }
                        }
                        LocalDate fechaOperacion = LocalDate.now();

                        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Deposito");
                        
                        List<Movimiento> movimientos = cuentasBancarias.get(i).getMovimientos();
                        movimientos.add(movimiento);
                        cuentasBancarias.get(i).setMovimientos(movimientos);

                        double saldoFinal = cuentasBancarias.get(i).getSaldo() + monto;
                        cuentasBancarias.get(i).setSaldo(saldoFinal);
                        
                        return "Deposito realizado";
                    }
                }
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay cuentas bancarias";
        }
        return "Error: la cuenta bancaria no existe";
    }

    public static String retirar(String montoString, String idCuentaBancariaString){
        cuentasBancarias=Banco.getCuentasBancarias();
        
        if (cuentasBancarias.size()!=0) {
            if (ValidacionesOperacion.montoValido(montoString) && ValidacionesEntradas.intValido(idCuentaBancariaString) && Integer.parseInt(idCuentaBancariaString)>=0) {
                double monto=Double.parseDouble(montoString);
                int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
                for(int i=0;i<cuentasBancarias.size();i++) {
                    if (cuentasBancarias.get(i).getId()==idCuentaBancaria) {
                        if (cuentasBancarias.get(i).getSaldo()>=monto) {
                            int idMovimiento=0;
                            if (cuentasBancarias.get(i).getMovimientos().size()!=0) {
                                for(int j=0;j<cuentasBancarias.get(i).getMovimientos().size();j++) {
                                    idMovimiento=cuentasBancarias.get(i).getMovimientos().get(i).getId()+1;
                                }
                            }
                            LocalDate fechaOperacion = LocalDate.now();

                            Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Retiro");

                            List<Movimiento> movimientos = cuentasBancarias.get(i).getMovimientos();
                            movimientos.add(movimiento);
                            cuentasBancarias.get(i).setMovimientos(movimientos);

                            double saldoFinal = cuentasBancarias.get(i).getSaldo() - monto;
                            cuentasBancarias.get(i).setSaldo(saldoFinal);

                            return "Retiro realizado";
                        }else{
                            return "Error: saldo insuficiente";
                        }
                    }
                }
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay cuentas bancarias";
        }
        return "Error: la cuenta bancaria no existe";
    }

    public static String transferir(String montoString, String idCuentaBancariaOrigenString, String idCuentaBancariaDestinoString){
        cuentasBancarias=Banco.getCuentasBancarias();

        if (cuentasBancarias.size()!=0) {
            if (ValidacionesOperacion.montoValido(montoString) && ValidacionesEntradas.intValido(idCuentaBancariaOrigenString) && Integer.parseInt(idCuentaBancariaOrigenString)>=0 && ValidacionesEntradas.intValido(idCuentaBancariaDestinoString) && Integer.parseInt(idCuentaBancariaDestinoString)>=0 && Integer.parseInt(idCuentaBancariaOrigenString)!=Integer.parseInt(idCuentaBancariaDestinoString)) {
                double monto=Double.parseDouble(montoString);
                int idCuentaBancariaOrigen=Integer.parseInt(idCuentaBancariaOrigenString);
                int idCuentaBancariaDestino=Integer.parseInt(idCuentaBancariaDestinoString);
                for(int i=0;i<cuentasBancarias.size();i++) {
                    if (cuentasBancarias.get(i).getId()==idCuentaBancariaOrigen) {
                        if (cuentasBancarias.get(i).getSaldo()>=monto) {
                            for(int j=0;j<cuentasBancarias.size();j++) {
                                if (cuentasBancarias.get(j).getId()==idCuentaBancariaDestino) {                                    
                                    int idMovimientoOrigen=0;
                                    if (cuentasBancarias.get(i).getMovimientos().size()!=0) {
                                        for(int k=0;k<cuentasBancarias.get(i).getMovimientos().size();k++) {
                                            idMovimientoOrigen=cuentasBancarias.get(i).getMovimientos().get(i).getId()+1;
                                        }
                                    }

                                    int idMovimientoDestino=0;
                                    if (cuentasBancarias.get(j).getMovimientos().size()!=0) {
                                        for(int k=0;k<cuentasBancarias.get(j).getMovimientos().size();k++) {
                                            idMovimientoDestino=cuentasBancarias.get(j).getMovimientos().get(i).getId()+1;
                                        }
                                    }

                                    LocalDate fechaOperacion = LocalDate.now();

                                    Movimiento movimientoOrigen = new Movimiento(idMovimientoOrigen,idCuentaBancariaOrigen,fechaOperacion,monto,"Transferencia enviada");
                                    Movimiento movimientoDestino = new Movimiento(idMovimientoDestino,idCuentaBancariaOrigen,fechaOperacion,monto,"Transferencia recibida");

                                    List<Movimiento> movimientos = cuentasBancarias.get(i).getMovimientos();
                                    movimientos.add(movimientoOrigen);
                                    cuentasBancarias.get(i).setMovimientos(movimientos);

                                    movimientos = cuentasBancarias.get(j).getMovimientos();
                                    movimientos.add(movimientoDestino);
                                    cuentasBancarias.get(j).setMovimientos(movimientos);
                                    
                                    double saldoFinalOrigen = cuentasBancarias.get(i).getSaldo() - monto;
                                    cuentasBancarias.get(i).setSaldo(saldoFinalOrigen);

                                    double saldoFinalDestino = cuentasBancarias.get(j).getSaldo() + monto;
                                    cuentasBancarias.get(j).setSaldo(saldoFinalDestino);

                                    return "Transferencia realizada";
                                }
                            }
                        }else{
                            return "Error: saldo insuficiente";
                        }
                    }
                }
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay cuentas bancarias";
        }
        return "Error: una de las cuentas bancarias no existe";
    }
}