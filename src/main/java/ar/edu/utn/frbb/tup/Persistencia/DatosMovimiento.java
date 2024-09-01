package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Movimiento;

@Repository
public class DatosMovimiento {
    private List<Movimiento> movimientos=new ArrayList<Movimiento>();

    public Movimiento buscarMovimiento(int idMovimiento) {
        //Se busca el movimiento con el ID entre los movimientos registrados y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getId()==idMovimiento) {
                return movimiento;
            }
        }
        return null;
    }

    public void agregarMovimiento(Movimiento movimiento) {
        //Se agrega el movimiento a la lista de movimientos.
        movimientos.add(movimiento);
    }

    public void eliminarMovimiento(Movimiento movimiento) {
        //Se elimina el movimiento de la lista de movimientos.
        movimientos.remove(movimiento);
    }

    public List<Movimiento> listarMovimientos() {
        //Se devuelve la lista entera de movimientos.
        return movimientos;
    }
}
