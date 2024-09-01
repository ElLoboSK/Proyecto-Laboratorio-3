package ar.edu.utn.frbb.tup.Persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;

@Repository
public class DatosCuentaBancaria {
    private List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public CuentaBancaria buscarCuentaBancariaId(int idCuentaBancaria) {
        //Se busca la cuenta bancaria con el ID entre las cuentas bancarias registradas y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getId()==idCuentaBancaria) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public CuentaBancaria buscarCuentaBancariaCbu(String cbu) {
        //Se busca la cuenta bancaria con el CBU entre las cuentas bancarias registradas y lo devuelve en caso de encontrarlo, en caso contrario devuelve null.
        for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getCbu().equals(cbu)) {
                return cuentaBancaria;
            }
        }
        return null;
    }

    public void agregarCuentaBancaria(CuentaBancaria cuentaBancaria) {
        //Se agrega la cuenta bancaria a la lista de cuentas bancarias.
        cuentasBancarias.add(cuentaBancaria);
    }

    public void eliminarCuentaBancaria(CuentaBancaria cuentaBancaria) {
        //Se elimina la cuenta bancaria de la lista de cuentas bancarias.
        cuentasBancarias.remove(cuentaBancaria);
    }

    public List<CuentaBancaria> listarCuentasBancarias() {
        //Se devuelve la lista entera de cuentas bancarias.
        return cuentasBancarias;
    }
}
