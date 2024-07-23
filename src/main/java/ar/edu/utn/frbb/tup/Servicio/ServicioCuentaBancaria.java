package ar.edu.utn.frbb.tup.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Presentacion.Entrada.Entradas;
import ar.edu.utn.frbb.tup.Presentacion.Salida.Consola;

public class ServicioCuentaBancaria {
    //se crean 2 listas para luego llamarlas desde la clase banco y actualizarlas si se modifican
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static void crearCuentaBancaria(int posicionCliente) {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //se ingresan los datos de la cuenta bancaria automaticamente en su mayoria
        int idCliente = clientes.get(posicionCliente).getId();
        LocalDate fechaApertura = LocalDate.now();
        double saldo = 0;

        //el cbu se hace con un random number generator y se valida que no exista en la lista de cuentas bancarias, si existe se repite el proceso
        boolean validCbu;
        String cbu;
        do{
            cbu=Math.round(Math.random()*(999999 - 100000 + 1) + 100000)+"";
            validCbu = true;
            for (int j=0;j<cuentasBancarias.size();j++){
                if (cuentasBancarias.get(j).getCbu().equals(cbu)){
                    validCbu = false;
                }
            }
        }while (!validCbu);
        
        //el tipo de cuenta es el unico dato que se ingresa manualmente a partir de un menu para minimizar error humano
        String tipoCuenta="";
        int respuesta;
        do{
            System.out.println("Ingrese el tipo de cuenta bancaria (1-Caja de Ahorros/2-Cuenta Corriente)");
            respuesta = Entradas.validInt();
            if (respuesta==1) {
                Consola.limpiarPantalla();
                tipoCuenta = "Caja de Ahorros";
            }else if (respuesta==2) {
                Consola.limpiarPantalla();
                tipoCuenta = "Cuenta Corriente";
            }else {
                Consola.limpiarPantalla();
                System.out.println("Error: opcion no valida");
            }
        }while (respuesta!=1 && respuesta!=2);
        
        //se define el id agarrando la cuenta bancaria con el id mas grande y sumandole 1
        int id=0;
        for (int j=0;j<cuentasBancarias.size();j++){
            id = cuentasBancarias.get(j).getId()+1;
        }
        
        //se crea la cuenta bancaria con los datos ingresados y se actualizan las listas para que el resto de las clases y metodos tengan acceso a la informacion
        CuentaBancaria cuentaBancaria = new CuentaBancaria(id,idCliente,fechaApertura,saldo,cbu,tipoCuenta);
        cuentasBancarias.add(cuentaBancaria);


        List<CuentaBancaria> cuentasCliente = clientes.get(posicionCliente).getCuentasBancarias();
        cuentasCliente.add(cuentaBancaria);
        clientes.get(posicionCliente).setCuentasBancarias(cuentasCliente);


        Consola.limpiarPantalla();
        System.out.println("Cuenta bancaria creada");

        Banco.setCuentasBancarias(cuentasBancarias);
        Banco.setClientes(clientes);
    }

    public static void mostrarCuentasBancarias(int posicionCliente) {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //se muestra la informacion de las cuentas bancarias por linea, si no hay cuentas bancarias se avisa por mensaje
        System.out.println("Cuentas Bancarias del cliente:");
        if (clientes.get(posicionCliente).getCuentasBancarias().size()==0){
            System.out.println("No hay cuentas bancarias");
        }else{
            for (int j=0;j<clientes.get(posicionCliente).getCuentasBancarias().size();j++){
                System.out.println("ID: "+clientes.get(posicionCliente).getCuentasBancarias().get(j).getId()+", CBU: "+clientes.get(posicionCliente).getCuentasBancarias().get(j).getCbu()+", Fecha de apretura: "+clientes.get(posicionCliente).getCuentasBancarias().get(j).getFechaApertura()+", Tipo: "+clientes.get(posicionCliente).getCuentasBancarias().get(j).getTipoCuenta());
            }
        }
    }

    public static void administrarCuentaBancaria(int posicionCuentaBancaria) {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();
        
        int menu;
        do{
            System.out.println("------------------------------");
            System.out.println("Menu de administracion de cuentas bancaria:");
            System.out.println("1-Mostrar Datos");
            System.out.println("2-Depositar");
            System.out.println("3-Retirar");
            System.out.println("4-Transferir");
            System.out.println("0-Volver");
            System.out.println("------------------------------");
            menu = Entradas.validInt();
            //se usan metodos de la clase cuenta bancaria y de la clase operaciones segun el caso
            switch (menu){
                case 1:
                    Consola.limpiarPantalla();
                    cuentasBancarias.get(posicionCuentaBancaria).mostrarDatos();
                    break;
                case 2:
                    Consola.limpiarPantalla();
                    ServicioOperacion.depositar(posicionCuentaBancaria);
                    break;
                case 3:
                    Consola.limpiarPantalla();
                    ServicioOperacion.retirar(posicionCuentaBancaria);
                    break;
                case 4:
                    Consola.limpiarPantalla();
                    ServicioOperacion.transferir(posicionCuentaBancaria);
                    break;
                case 0:
                    Consola.limpiarPantalla();
                    System.out.println("Volver");
                    break;
                default:
                    Consola.limpiarPantalla();
                    System.out.println("Error: opcion no valida");
                    break;
            }
        }while (menu!=0);
    }

    public static void eliminarCuentaBancaria(int posicionCliente) {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();
        
        //se elige la cuenta bancaria con un mensaje personalizado que se pasa por parametro
        String mensaje="\nElija la cuenta bancaria a eliminar usando el ID:";
        int posicion=elegirCuentaBancaria(clientes, mensaje, posicionCliente);
        
        int respuesta;
        //la posicion en el metodo por default es -1, lo que quiere decir que no si no se encontro la cuenta bancaria buscada, va a traer -1 y no se ejecuta el resto del codigo
        if (posicion!=-1) {
            if (clientes.get(posicionCliente).getCuentasBancarias().get(posicion).getSaldo()>0) {
                //si la cuenta bancaria tiene saldo no podra ser eliminada
                Consola.limpiarPantalla();
                System.out.println("Error: la cuenta bancaria no se puede eliminar porque aun tiene saldo");
            }else{
                Consola.limpiarPantalla();
                do {
                    System.out.println("¿Esta seguro que desea eliminar la cuenta bancaria? (1-Si/2-No)");
                    respuesta = Entradas.validInt();
                    //el usuario puede elegir si esta seguro de eliminar la cuenta bancaria
                    if (respuesta==1) {
                        //si la respuesta es afirmativa se elimina el cliente de las listas y se actualizan
                        for (int k=0;k<cuentasBancarias.size();k++){
                            if (cuentasBancarias.get(k).getId()==clientes.get(posicionCliente).getCuentasBancarias().get(posicion).getId()) {
                                cuentasBancarias.remove(k);
                                break;
                            }
                        }
                        clientes.get(posicionCliente).getCuentasBancarias().remove(posicion);

                        Consola.limpiarPantalla();
                        System.out.println("Cuenta bancaria eliminada");

                        Banco.setCuentasBancarias(cuentasBancarias);
                        Banco.setClientes(clientes);
                        break;
                    }else if (respuesta==2) {
                        //en caso negativo, se cancela la eliminacion
                        Consola.limpiarPantalla();
                        System.out.println("Se cancela la eliminacion de cuenta bancaria");
                        break;
                    }else{
                        Consola.limpiarPantalla();
                        System.out.println("Error: opcion no valida");
                    }
                } while (respuesta != 1 && respuesta!= 2);
            }
        }
    }

    //Metodos auxiliares
    public static int elegirCuentaBancaria(List<Cliente> clientes, String mensaje, int posicionCliente) {
        //por defalut, la posicion es -1, esto es asi porque no existe esa posicion en una lista, en lugar de usar un booleano que valide y un int para la posicion en 2 variables distintas, junto todo en una variable haciendo que el -1 signifique que la cuenta bancaria no fue encontrada
        int posicion=-1;
        
        //se muestran todas las cuentas bancarias del cliente seleccionado para facilitar la eleccion
        System.out.println("Cuentas Bancarias del cliente:");
        if (clientes.get(posicionCliente).getCuentasBancarias().size()==0){
            System.out.println("No hay cuentas bancarias");
        }else{
            for (int i=0;i<clientes.get(posicionCliente).getCuentasBancarias().size();i++){
                System.out.println("ID: "+clientes.get(posicionCliente).getCuentasBancarias().get(i).getId()+", CBU: "+clientes.get(posicionCliente).getCuentasBancarias().get(i).getCbu()+", Fecha de apretura: "+clientes.get(posicionCliente).getCuentasBancarias().get(i).getFechaApertura()+", Tipo: "+clientes.get(posicionCliente).getCuentasBancarias().get(i).getTipoCuenta());
            }
            //se usa el id de la cuenta para seleccionarla y muestra por pantalla un mensaje personalizado pasado por parametro
            System.out.println(mensaje);
            int id = Entradas.validInt();
            
            //si se encuentra la cuenta bancaria, guarda la posicion en la variable posicion
            for (int i=0;i<clientes.get(posicionCliente).getCuentasBancarias().size();i++){
                if (clientes.get(posicionCliente).getCuentasBancarias().get(i).getId()==id){
                    posicion=i;
                }
            }
            //si no se encuentra la cuenta, se mantiene el -1 y muestra un mensaje de que la cuenta bancaria no existe en la parte de la base de datos referida a ese cliente
            if (posicion==-1) {
                Consola.limpiarPantalla();
                System.out.println("La cuenta bancaria no existe");    
            }
        }
        return posicion;
    }
}
