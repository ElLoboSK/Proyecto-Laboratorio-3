package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;

public class DatosCuentaBancaria {
    private static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static CuentaBancaria buscarCuentaBancariaId(int idCuentaBancaria) {
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getId()==idCuentaBancaria) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public static CuentaBancaria buscarCuentaBancariaCbu(String cbu) {
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getCbu().equals(cbu)) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public static List<CuentaBancaria> listarCuentaBancariasCliente(int idCliente) {
        List<CuentaBancaria> cuentasBancariasCliente = new ArrayList<CuentaBancaria>();
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getIdCliente()==idCliente) {
                cuentasBancariasCliente.add(cuentaBancaria);
            }
        }
        return cuentasBancariasCliente;
    }

    public static void setCuentasBancarias(List<CuentaBancaria> cuentasBancariasActualizadas) {
        cuentasBancarias=cuentasBancariasActualizadas;
    }

    public static List<CuentaBancaria> getCuestasBancarias() {
        return cuentasBancarias;
    }
}
