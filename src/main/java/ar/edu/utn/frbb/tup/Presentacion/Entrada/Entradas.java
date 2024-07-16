package ar.edu.utn.frbb.tup.Presentacion.Entrada;

import java.util.Scanner;

public class Entradas {
    static Scanner scanner = new Scanner(System.in);

    //validaciones de tipos de datos
    public static int validInt() {
        int numero = 0;
        String entrada;
        boolean valido = false;

        //se repite el ingreso hasta que se ingrese un numero entero valido, si se ingresa un tipo de dato incorrecto el try catch lo detecta y marca un error al usuario
        do{
            try {
                entrada = scanner.nextLine();
                numero = Integer.valueOf(entrada);
                valido = true;
            } catch (Exception e) {
                System.out.println("Error: tipo de dato invalido");
                valido = false;
            }
        } while (!valido);
        
        return numero;
    }

    public static double validDouble() {
        double numero = 0;
        String entrada;
        boolean valido = false;

        //se repite el ingreso hasta que se ingrese un numero real valido, si se ingresa un tipo de dato incorrecto el try catch lo detecta y marca un error al usuario
        do{
            try {
                entrada = scanner.nextLine();
                numero = Double.valueOf(entrada);
                valido = true;
            } catch (Exception e) {
                System.out.println("Error: tipo de dato invalido");
                valido = false;
            }
        } while (!valido);
        
        //luego de validar que sea un numero real, se trunca a 2 decimales para que quede mas presentable
        double factor = Math.pow(10, 2);
        numero = Math.floor(numero*factor)/factor;

        return numero;
    }

    public static String validString() {
        String cadena = "";

        //se repite el ingreso hasta que no el string no sea un vacio
        do{
            cadena = scanner.nextLine();
            if (cadena.length() == 0) {
                System.out.println("Error: cadena invalida");
            }
        }while (cadena.length() == 0);
        return cadena;
    }
}

