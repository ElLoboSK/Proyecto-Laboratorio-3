package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Presentacion.Entrada.Entradas;
import ar.edu.utn.frbb.tup.Presentacion.Salida.Consola;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioOperacion {
    //se crean 2 listas para luego llamarlas desde la clase banco y actualizarlas si se modifican
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static void depositar(int posicionCuentaBancaria){
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //se genera el id buscando el id mas alto en la lista y sumandole 1
        int idMovimiento=0;
        if (cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size()!=0) {
            for(int i=0;i<cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size();i++) {
                idMovimiento=cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().get(i).getId()+1;
            }
        }

        //se generan varios datos automaticamente
        int idCuentaBancaria = cuentasBancarias.get(posicionCuentaBancaria).getId();
        LocalDate fechaOperacion = LocalDate.now();

        //finalmente se pide el monto a depositar
        System.out.println("Ingrese el monto a depositar:");
        double monto = Entradas.validDouble();

        //se actualizan las listas creando un nuevo movimiento y sumando el monto ingresado a el saldo actual de la cuenta
        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Deposito");
        List<Movimiento> movimientos = cuentasBancarias.get(posicionCuentaBancaria).getMovimientos();
        movimientos.add(movimiento);
        cuentasBancarias.get(posicionCuentaBancaria).setMovimientos(movimientos);

        double saldoFinal = cuentasBancarias.get(posicionCuentaBancaria).getSaldo() + monto;
        cuentasBancarias.get(posicionCuentaBancaria).setSaldo(saldoFinal);

        Consola.limpiarPantalla();
        System.out.println("Deposito realizado");
    }

    public static void retirar(int posicionCuentaBancaria){
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //en caso de que el saldo sea 0, no se va a ejecutar el codigo
        if (cuentasBancarias.get(posicionCuentaBancaria).getSaldo()==0) {
            Consola.limpiarPantalla();
            System.out.println("No hay saldo");
        }else{
            //se genera nuevamente el id usando el id mas grande en la lista y sumandole 1
            int idMovimiento=0;
            if (cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size()!=0) {
                for(int i=0;i<cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().size();i++) {
                    idMovimiento=cuentasBancarias.get(posicionCuentaBancaria).getMovimientos().get(i).getId()+1;
                }
            }
            
            //se generan otros datos automaticamente
            int idCuentaBancaria = cuentasBancarias.get(posicionCuentaBancaria).getId();
            LocalDate fechaOperacion = LocalDate.now();
    
            boolean valido=false;
            double monto;
            //se pide el monto a retirar, en caso de que el monto supere al saldo acutal de la cuenta, se va a avisar por pantalla y se va a pedir nuevamente el monto
            do{
                System.out.println("Ingrese el monto a retirar:");
                monto = Entradas.validDouble();
                if (monto<=cuentasBancarias.get(posicionCuentaBancaria).getSaldo()) {
                    valido=true;
                }else {
                    Consola.limpiarPantalla();
                    System.out.println("Error: saldo insuficiente");
                }
    
            }while (!valido);
    
            //se crea el movimiento y se actualizan las listas, tambien se resta el monto ingresado al saldo actual de la cuenta
            Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,fechaOperacion,monto,"Retiro");
            List<Movimiento> movimientos = cuentasBancarias.get(posicionCuentaBancaria).getMovimientos();
            movimientos.add(movimiento);
            cuentasBancarias.get(posicionCuentaBancaria).setMovimientos(movimientos);
    
            double saldoFinal = cuentasBancarias.get(posicionCuentaBancaria).getSaldo() - monto;
            cuentasBancarias.get(posicionCuentaBancaria).setSaldo(saldoFinal);
    
            Consola.limpiarPantalla();
            System.out.println("Retiro realizado");
        }
    }

    public static void transferir(int posicionCuentaBancaria){
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
    }
}