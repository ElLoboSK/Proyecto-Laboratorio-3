package ar.edu.utn.frbb.tup.Presentacion;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Presentacion.Entrada.Entradas;
import ar.edu.utn.frbb.tup.Presentacion.Salida.Consola;
import ar.edu.utn.frbb.tup.Servicio.ServicioCliente;
import ar.edu.utn.frbb.tup.Servicio.ServicioCuentasBancaria;

public class Menus {
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static void menuPrincipal() {
        int menu;

        Consola.limpiarPantalla();
        do {
            clientes=Banco.getClientes();
            cuentasBancarias=Banco.getCuentasBancarias();
            
            System.out.println("------------------------------");
            System.out.println("Menu principal:");
            System.out.println("1-Clientes");
            System.out.println("2-Cuentas Bancarias");
            System.out.println("0-Salir");
            System.out.println("------------------------------");
            menu = Entradas.validInt();
            switch (menu) {
                case 1:
                    Consola.limpiarPantalla();
                    menuClientes();
                    break;
                case 2:
                    //se elije el cliente sobre el que se va a trabajar las cuentas bancarias, se guarda la posicion de este en la lista y se lo pasa a al menu de cuentas bancarias
                    Consola.limpiarPantalla();
                    String mensaje="\nElija el cliente del que desea administrar las cuentas bancarias usando el DNI:";
                    int posicionCliente = ServicioCliente.elegirCliente(clientes,mensaje);
                    if (posicionCliente!=-1) {
                        menuCuentasBancarias(posicionCliente);
                    }
                    break;
                case 0:
                    Consola.limpiarPantalla();
                    System.out.println("Salir");
                    break;
                default:
                    Consola.limpiarPantalla();
                    System.out.println("Error: opcion no valida");
            }
        } while (menu!= 0);
    }

    public static void menuClientes() {
        int menu;

        do{
            clientes=Banco.getClientes();
            cuentasBancarias=Banco.getCuentasBancarias();

            System.out.println("------------------------------");
            System.out.println("Menu de clientes:");
            System.out.println("1-Crear Cliente");
            System.out.println("2-Mostrar Clientes");
            System.out.println("3-Modificar Cliente");
            System.out.println("4-Eliminar Cliente");
            System.out.println("0-Volver");
            System.out.println("------------------------------");
            menu = Entradas.validInt();
            switch (menu){
                case 1:
                    Consola.limpiarPantalla();
                    ServicioCliente.crearCliente();
                    break;
                case 2:
                    Consola.limpiarPantalla(); 
                    ServicioCliente.mostrarClientes();
                    break;
                case 3:
                    //se elije el cliente sobre el que se va a trabajar, se guarda la posicion de este en la lista y se lo pasa a al menu de modificar cliente
                    Consola.limpiarPantalla();
                    String mensaje="\nElija el cliente a modificar usando el DNI:";
                    int posicion=ServicioCliente.elegirCliente(clientes, mensaje);
                    if (posicion!=-1) {
                        ServicioCliente.modificarCliente(posicion);
                    }
                    break;
                case 4:
                    Consola.limpiarPantalla();
                    ServicioCliente.eliminarCliente();
                    break;
                case 0:
                    Consola.limpiarPantalla();
                    System.out.println("Volver");
                    break;
                default:
                    Consola.limpiarPantalla();
                    System.out.println("Error: opcion no valida");
            }
        }while (menu!=0);
    }

    public static void menuCuentasBancarias(int posicionCliente) {
        int menu;

        do{
            clientes=Banco.getClientes();
            cuentasBancarias=Banco.getCuentasBancarias();

            System.out.println("------------------------------");
            System.out.println("Menu de cuentas bancarias:");
            System.out.println("1-Crear Cuenta Bancaria");
            System.out.println("2-Mostrar Cuentas Bancarias");
            System.out.println("3-Administrar Cuenta Bancaria");
            System.out.println("4-Eliminar Cuenta Bancaria");
            System.out.println("0-Volver");
            System.out.println("------------------------------");
            menu = Entradas.validInt();
            switch (menu){
                case 1:
                    Consola.limpiarPantalla();
                    ServicioCuentasBancaria.crearCuentaBancaria(posicionCliente);
                    break;
                case 2:
                    Consola.limpiarPantalla();
                    ServicioCuentasBancaria.mostrarCuentasBancarias(posicionCliente);
                    break;
                case 3:
                    //se elije la cuenta bancaria sobre la que se va a trabajar, se guarda la posicion de esta en la lista y se lo pasa a al menu de administrar cuenta bancaria
                    Consola.limpiarPantalla();
                    String mensaje="\nElija la cuenta bancaria a utilizar usando el ID:";
                    int posicionCuentaCliente=ServicioCuentasBancaria.elegirCuentaBancaria(clientes, mensaje, posicionCliente);
                    int posicionCuentaBancaria=-1;
                    if (posicionCuentaCliente!=-1) {
                        for (int i=0;i<cuentasBancarias.size();i++) {
                            if (clientes.get(posicionCliente).getCuentasBancarias().get(posicionCuentaCliente).getId()==cuentasBancarias.get(i).getId()) {
                                posicionCuentaBancaria=i;
                                break;
                            }
                        }
                        if (posicionCuentaBancaria!=-1) {
                            ServicioCuentasBancaria.administrarCuentaBancaria(posicionCuentaBancaria);
                        }
                    }
                    break;
                case 4:
                    Consola.limpiarPantalla();
                    ServicioCuentasBancaria.eliminarCuentaBancaria(posicionCliente);
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
}
