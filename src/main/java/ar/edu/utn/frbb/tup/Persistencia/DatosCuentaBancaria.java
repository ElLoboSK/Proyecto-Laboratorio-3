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

    public List<CuentaBancaria> listarCuentaBancariasCliente(int idCliente) {
        List<CuentaBancaria> cuentasBancariasCliente = new ArrayList<CuentaBancaria>();
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getIdCliente()==idCliente) {
                cuentasBancariasCliente.add(cuentaBancaria);
            }
        }
        return cuentasBancariasCliente;
    }

    public void setCuentasBancarias(List<CuentaBancaria> cuentasBancarias) {
        this.cuentasBancarias=cuentasBancarias;
    }

    public List<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }
}
