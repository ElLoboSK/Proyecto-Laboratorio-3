package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.Movimiento;

public class DatosMovimiento {
    private static List<Movimiento> movimientos=new ArrayList<Movimiento>();

    public static Movimiento buscarMovimiento(int idMovimiento) {
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getId()==idMovimiento) {
                return movimiento;
            }
        }
        return null;
    }

    public static List<Movimiento> listarMovimientosCuentaBancaria(int idCuentaBancaria) {
        List<Movimiento> movimientosCuentaBancaria=new ArrayList<Movimiento>();
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getIdCuentaBancaria()==idCuentaBancaria) {
                movimientosCuentaBancaria.add(movimiento);
            }
        }
        return movimientosCuentaBancaria;
    }

    public static void setMovimientos(List<Movimiento> movimientosActualizados) {
        movimientos=movimientosActualizados;
    }

    public static List<Movimiento> getMovimientos() {
        return movimientos;
    }
}
