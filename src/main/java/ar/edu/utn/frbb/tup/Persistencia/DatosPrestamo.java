package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Prestamo;

public class DatosPrestamo {
    private static List<Prestamo> prestamos = new ArrayList<Prestamo>();

    public static Prestamo buscarPrestamo(int idPrestamo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId()==idPrestamo) {
                return prestamo;
            }
        }
        return null;
    }

    public static void setPrestamos(List<Prestamo> prestamos) {
        DatosPrestamo.prestamos = prestamos;
    }

    public static List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
