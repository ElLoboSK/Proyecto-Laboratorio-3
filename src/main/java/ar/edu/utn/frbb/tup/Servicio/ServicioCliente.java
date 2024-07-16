package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Presentacion.Entrada.Entradas;
import ar.edu.utn.frbb.tup.Presentacion.Salida.Consola;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.Validaciones;

public class ServicioCliente {
    //se crean 2 listas para luego llamarlas desde la clase banco y actualizarlas si se modifican
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static void crearCliente() {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();

        //Ingreso de datos del cliente
        System.out.println("Ingrese el nombre:");
        String nombre = Entradas.validString();
        System.out.println("Ingrese el apellido:");
        String apellido = Entradas.validString();
        boolean valido = false;
        //se valida que el deni ingresado no figure en otro cliente
        System.out.println("Ingrese el DNI (8 digitos):");
        String dni;
        do{
            dni = Validaciones.validDni();
            valido = true;
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni().equals(dni)){
                    valido = false;
                    System.out.println("Error: ya existe un cliente con ese DNI");
                    break;
                }
            }
        }while (!valido);
        System.out.println("Ingrese el telefono:");
        String telefono = Entradas.validString();
        //se define el id agarrando el cliente con el id mas grande y sumandole 1
        int id = 0;
        for (int i=0;i<clientes.size();i++){
            id = clientes.get(i).getId()+1;
        }
    
        //se crea el cliente y se agrega a la lista
        Cliente cliente = new Cliente(nombre, apellido, dni, telefono, id);
        clientes.add(cliente);

        Consola.limpiarPantalla();
        System.out.println("Cliente creado");
        
        //finalmente se actualiza la lista de clientes en la clase banco para que el resto de clases tengan acceso a la actualizacion
        Banco.setClientes(clientes);
    }

    public static void mostrarClientes() {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        
        //si no hay clientes se muestra un mensaje, sino se muestran cada cliente en 1 linea
        System.out.println("Clientes:");
        if (clientes.size()==0){
            System.out.println("No hay clientes");
        }else{
            for (int i=0;i<clientes.size();i++){
                System.out.println("ID: "+clientes.get(i).getId()+", Nombre: "+clientes.get(i).getNombre()+", Apellido: "+clientes.get(i).getApellido()+", DNI: "+clientes.get(i).getDni()+", Telefono: "+clientes.get(i).getTelefono());
            }
        }
    }

    public static void modificarCliente(int posicionCliente) {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();

        int menu = 0;
        //se muestra la informacion actual del cliente y debajo las opciones de modificacion
        do{
            System.out.println("------------------------------");
            System.out.println("Informacion actual del cliente:");
            System.out.println("Id: "+clientes.get(posicionCliente).getId());
            System.out.println("Nombre: "+clientes.get(posicionCliente).getNombre());
            System.out.println("Apellido: "+clientes.get(posicionCliente).getApellido());
            System.out.println("DNI: "+clientes.get(posicionCliente).getDni());
            System.out.println("Telefono: "+clientes.get(posicionCliente).getTelefono());
            System.out.println("\nMenu de modificacion:");
            System.out.println("1-Nombre");
            System.out.println("2-Apellido");
            System.out.println("3-Telefono");
            System.out.println("0-Volver");
            System.out.println("------------------------------");
            menu = Entradas.validInt();
            //los datos se actualizan usando los setters y getters de la clase cliente
            switch (menu) {
                case 1:
                    Consola.limpiarPantalla();
                    System.out.println("Ingrese el nuevo nombre:");
                    String nombre = Entradas.validString();
                    clientes.get(posicionCliente).setNombre(nombre);
                    Consola.limpiarPantalla();
                    break;
                case 2:
                    Consola.limpiarPantalla();
                    System.out.println("Ingrese el nuevo apellido:");
                    String apellido = Entradas.validString();
                    clientes.get(posicionCliente).setApellido(apellido);
                    Consola.limpiarPantalla();
                    break;
                case 3:
                    Consola.limpiarPantalla();
                    System.out.println("Ingrese el nuevo telefono:");
                    String telefono = Entradas.validString();
                    clientes.get(posicionCliente).setTelefono(telefono);
                    Consola.limpiarPantalla();
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

    public static void eliminarCliente() {
        //se actualiza la informacion de las listas antes de trabajar con ellas
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        //se elige el cliente a eliminar usando una funcion auxiliar para no repetir codigo innecesariamente, se pasa un mensaje personalizado por parametros
        String mensaje="\nElija el cliente a eliminar usando el DNI:";
        int posicion=elegirCliente(clientes, mensaje);
        
        boolean valido=true;
        //la posicion en el metodo por default es -1, lo que quiere decir que no si no se encontro al cliente buscado, va a traer -1 y no se ejecuta el resto del codigo
        if (posicion!=-1) {
            int respuesta;

            Consola.limpiarPantalla();
            do {
                System.out.println("En el caso de que ninguna cuenta bancaria tenga saldo, al eliminar el cliente se eliminaran sus cuentas tambien");
                System.out.println("¿Esta seguro que desea eliminar el cliente? (1-Si/2-No)");
                respuesta = Entradas.validInt();
                //el usuario puede elegir si esta seguro de eliminar el cliente o no
                if (respuesta==2) {
                    //caso negativo, la eliminacion se cancela
                    Consola.limpiarPantalla();
                    System.out.println("Se cancela la eliminacion de cliente");
                    break;
                }else if (respuesta==1) {
                    //caso positivo, se elimina el cliente buscandolo con la posicion previamente elegida
                    if (clientes.get(posicion).getCuentasBancarias().size()!=0) {
                        for (int j=0;j<clientes.get(posicion).getCuentasBancarias().size();j++){
                            if (posicion==j) {
                                //si el cliente tiene saldo en alguna cuenta bancaria, no se podra eliminar
                                if (cuentasBancarias.get(j).getSaldo()>0) {
                                    Consola.limpiarPantalla();
                                    System.out.println("Error: el cliente no se puede eliminar porque tiene cuentas bancarias con saldo");
                                    valido = false;
                                }
                            }
                        }
                    }
                    if (valido) {
                        //se elimina el cliente y se actualiza la informacion de las listas
                        Consola.limpiarPantalla();
                        System.out.println("Cliente eliminado");
                        clientes.remove(posicion);

                        Banco.setClientes(clientes);
                        break;
                    }
                }else {
                    Consola.limpiarPantalla();
                    System.out.println("Error: opcion no valida");
                }
            } while (respuesta!=1 && respuesta!=2);
        }
    }

    //Metodos auxiliares
    public static int elegirCliente(List<Cliente> clientes, String mensaje) {
        //por defalut, la posicion es -1, esto es asi porque no existe esa posicion en una lista, en lugar de usar un booleano que valide y un int para la posicion en 2 variables distintas, junto todo en una variable haciendo que el -1 signifique que el cliente no fue encontrado
        int posicion=-1;

        //se muestran todos los clientes en pantalla para faicilitar la eleccion, en caso de no haber clientes se muestra un mensaje comentandolo
        System.out.println("Clientes:");
        if (clientes.size()==0){
            System.out.println("No hay clientes");
        }else{
            for (int i=0;i<clientes.size();i++){
                System.out.println("ID: "+clientes.get(i).getId()+", Nombre: "+clientes.get(i).getNombre()+", Apellido: "+clientes.get(i).getApellido()+", DNI:"+clientes.get(i).getDni());
            }
            //luego de la muestra de clientes, se usa el mensaje presonalizado que se trae por parametro y se usa el dni para elegir el cliente
            System.out.println(mensaje);
            String dni = Validaciones.validDni();

            //si se encuentra el cliente, se devuelve la posicion en la lista
            for (int i=0;i<clientes.size();i++){
                if (clientes.get(i).getDni().equals(dni)){
                    posicion = i;
                }
            }
            //si no se encuentra el cliente, se mantiene el -1 y se devuelve un mensaje de que el cliente no existe en la base de datos
            if (posicion==-1){
                Consola.limpiarPantalla();
                System.out.println("El cliente no existe");
            }
        }
        return posicion;
    }
}
