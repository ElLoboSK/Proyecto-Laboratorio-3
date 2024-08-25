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

    public List<Movimiento> listarMovimientosCuentaBancaria(int idCuentaBancaria) {
        List<Movimiento> movimientosCuentaBancaria=new ArrayList<Movimiento>();
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getIdCuentaBancaria()==idCuentaBancaria) {
                movimientosCuentaBancaria.add(movimiento);
            }
        }
        return movimientosCuentaBancaria;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos=movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
}
