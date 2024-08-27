package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;

@Repository
public class DatosCuentaBancaria {
    private List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public CuentaBancaria buscarCuentaBancariaId(int idCuentaBancaria) {
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getId()==idCuentaBancaria) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public CuentaBancaria buscarCuentaBancariaCbu(String cbu) {
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getCbu().equals(cbu)) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public void agregarCuentaBancaria(CuentaBancaria cuentaBancaria) {
        cuentasBancarias.add(cuentaBancaria);
    }

    public void eliminarCuentaBancaria(CuentaBancaria cuentaBancaria) {
        cuentasBancarias.remove(cuentaBancaria);
    }

    public List<CuentaBancaria> listarCuentasBancarias() {
        return cuentasBancarias;
    }
}
