package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.Movimiento;

@Repository
public class DatosMovimiento {
    private List<Movimiento> movimientos=new ArrayList<Movimiento>();

    public Movimiento buscarMovimiento(int idMovimiento) {
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getId()==idMovimiento) {
                return movimiento;
            }
        }
        return null;
    }

    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
    }

    public void eliminarMovimiento(Movimiento movimiento) {
        movimientos.remove(movimiento);
    }

    public List<Movimiento> listarMovimientos() {
        return movimientos;
    }
}
