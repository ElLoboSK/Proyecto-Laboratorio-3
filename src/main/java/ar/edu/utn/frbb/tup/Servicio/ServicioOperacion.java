package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioOperacion {
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static String depositar(String montoString, String idCuentaBancariaString) {
        cuentasBancarias=Banco.getCuentasBancarias();

        if (cuentasBancarias.size()!=0) {
            if (ValidacionesEntradas.doubleValido(montoString) && Double.parseDouble(montoString)>=0 && ValidacionesEntradas.intValido(idCuentaBancariaString) && Integer.parseInt(idCuentaBancariaString)>=0) {
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
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();
        if (cuentasBancarias.size()!=0) {
            if (ValidacionesEntradas.doubleValido(montoString) && Double.parseDouble(montoString)>=0 && ValidacionesEntradas.intValido(idCuentaBancariaString) && Integer.parseInt(idCuentaBancariaString)>=0) {
                double monto=Double.parseDouble(montoString);
                int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
                for(int i=0;i<cuentasBancarias.size();i++) {
                    if (cuentasBancarias.get(i).getId()==idCuentaBancaria) {
                        if (cuentasBancarias.get(i).getSaldo()>monto) {
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

    /*public static void transferir(int posicionCuentaBancaria){
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //en caso de que el saldo sea 0, no se va a ejecutar el codigo
        if (cuentasBancarias.get(posicionCuentaBancaria).getSaldo()==0) {
            Consola.limpiarPantalla();
            System.out.println("No hay saldo");
        }else{
            //se muestran TODAS las cuentas bancarias para transferir para facilitar la eleccion, en caso de no haber cuentas bancarias no se ejecuta el resto del codigo
            System.out.println("Cuentas bancarias para transferir:");
            if (cuentasBancarias.size()==1) {
                System.out.println("No hay cuentas bancarias para transferir");
            }else{
                for(int i=0;i<cuentasBancarias.size();i++) {
                    if (i!=posicionCuentaBancaria) {
                        System.out.println("ID: "+cuentasBancarias.get(i).getId()+", CBU: "+cuentasBancarias.get(i).getCbu()+", Fecha de apertura: "+cuentasBancarias.get(i).getFechaApertura());
                    }
                }
                System.out.println("\nIngrese el CBU de la cuenta bancaria a transferir:");
                String cbu=ValidacionesCuentaBancaria.validCBU();
                
                //se usa el cbu para buscar la cuenta, si se encuentra, se guarda la posicion de la misma
                int posicionCuentaBancariaTransferir=-1;
                for (int i=0;i<cuentasBancarias.size();i++) {
                    if (cuentasBancarias.get(i).getCbu().equals(cbu)) {
                        posicionCuentaBancariaTransferir=i;
                    }
                }
    
                //si no se encuentra se avisa por pantalla diciendo que la cuenta que busca no existe en la base de datos
                if (posicionCuentaBancariaTransferir==-1) {
                    Consola.limpiarPantalla();
                    System.out.println("La cuenta bancaria no existe");
                }else{
                    //se crea varios datos automaticamente
                    int idMovimiento=0;
                    if (cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size()!=0) {
                        for(int i=0;i<cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size();i++) {
                            idMovimiento=cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().get(i).getId()+1;
                        }
                    }
                
                    int idCuentaBancaria = cuentasBancarias.get(posicionCuentaBancaria).getId();
                    LocalDate fechaOperacion = LocalDate.now();
                
                    boolean validoMonto=false;
                    double monto;
                    //se pide el monto a transferir, si el monto supera el saldo acutal en la cuenta bancaria, se vuelve a pedir
                    do{
                        System.out.println("Ingrese el monto a transferir:");
                        monto = Entradas.validDouble();
                        if (monto<=cuentasBancarias.get(posicionCuentaBancaria).getSaldo()) {
                            validoMonto=true;
                        }else {
                            Consola.limpiarPantalla();
                            System.out.println("Error: saldo insuficiente");
                        }
                
                    }while (!validoMonto);
                
                    //se crean los movimientos para para cada cuenta, la de que emite y la que recibe, a su vez, se actualizan las listas de ambas cuentas bancarias
                    Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Transferencia enviada");
                    List<Movimiento> movimientos = cuentasBancarias.get(posicionCuentaBancaria).getMovimientos();
                    movimientos.add(movimiento);
                    cuentasBancarias.get(posicionCuentaBancaria).setMovimientos(movimientos);
                
                    //se hacen las operaciones para actualizar los saldos de cada cuenta bancaria
                    double saldoFinal = cuentasBancarias.get(posicionCuentaBancaria).getSaldo() - monto;
                    cuentasBancarias.get(posicionCuentaBancaria).setSaldo(saldoFinal);
    
                    Movimiento movimientoTransferir = new Movimiento(idMovimiento,cuentasBancarias.get(posicionCuentaBancariaTransferir).getId(),fechaOperacion,monto,"Transferencia recibida");

                    movimientos = cuentasBancarias.get(posicionCuentaBancariaTransferir).getMovimientos();
                    movimientos.add(movimientoTransferir);
                    cuentasBancarias.get(posicionCuentaBancariaTransferir).setMovimientos(movimientos);

                    saldoFinal = cuentasBancarias.get(posicionCuentaBancariaTransferir).getSaldo() + monto;
                    cuentasBancarias.get(posicionCuentaBancariaTransferir).setSaldo(saldoFinal);
                
                    Consola.limpiarPantalla();
                    System.out.println("Transferencia realizada");
                }
            }
        }
    }*/
}