package ar.edu.utn.frbb.tup.Servicio.Validaciones;

import ar.edu.utn.frbb.tup.Presentacion.Entrada.Entradas;

public class ValidacionesCuentaBancaria {
    public static String validCBU() {
        String cbu;
        int numero;
        boolean valido = false;

        //validacion especial para el CBU, solo permite ingresar numeros entre 100000 y 999999, tampoco es realista pero es para hacer mas facil de probar el programa y tenga validacion rapida
        do{
            numero = Entradas.validInt();
            if (numero > 100000 && numero < 999999) {
                valido=true;
            }
            if (!valido) {
                System.out.println("Error: CBU invalido");
            }
        }while (!valido);
        cbu=String.valueOf(numero);
        return cbu;
    }
}
    