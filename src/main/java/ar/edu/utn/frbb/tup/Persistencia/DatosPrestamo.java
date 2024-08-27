package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Prestamo;

@Repository
public class DatosPrestamo {
    private List<Prestamo> prestamos=new ArrayList<Prestamo>();

    public Prestamo buscarPrestamo(int idPrestamo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId()==idPrestamo) {
                return prestamo;
            }
        }
        return null;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        prestamos.remove(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return prestamos;
    }
}
