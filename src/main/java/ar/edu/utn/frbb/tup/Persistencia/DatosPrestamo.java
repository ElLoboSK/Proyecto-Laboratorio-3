package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Prestamo;

@Repository
public class DatosPrestamo {
    private List<Prestamo> prestamos=new ArrayList<Prestamo>();

    public Prestamo buscarPrestamo(int idPrestamo) {
        ///Se busca el prestamo con el ID entre los prestamos registrados y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId()==idPrestamo) {
                return prestamo;
            }
        }
        return null;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        //Se agrega el prestamo a la lista de prestamos.
        prestamos.add(prestamo);
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        //Se elimina el prestamo de la lista de prestamos.
        prestamos.remove(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        //Se devuelve la lista entera de prestamos.
        return prestamos;
    }
}
