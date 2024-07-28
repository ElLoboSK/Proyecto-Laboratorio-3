package ar.edu.utn.frbb.tup.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioCuentaBancaria {
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<CuentaBancaria> cuentasBancarias=new ArrayList<CuentaBancaria>();

    public static String crearCuentaBancaria(String dniString, String tipoCuenta) {
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();

        if (clientes.size()!=0){
            if (ValidacionesCliente.dniValido(dniString) && ValidacionesCuentaBancaria.tipoCuentaValido(tipoCuenta)) {
                int dni=Integer.parseInt(dniString);
                int idCliente=-1;
                int posicionCliente=-1;
                for (int i=0;i<clientes.size();i++){
                    if (clientes.get(i).getDni()==dni){
                        idCliente=clientes.get(i).getId();
                        posicionCliente=i;
                    }
                }
                if (idCliente==-1) {
                    return "Error: el cliente no existe";
                }
    
                boolean validCbu;
                String cbu;
                do{
                    cbu=Math.round(Math.random()*(999999 - 100000 + 1) + 100000)+"";
                    validCbu = true;
                    for (int j=0;j<cuentasBancarias.size();j++){
                        if (cuentasBancarias.get(j).getCbu().equals(cbu)){
                            validCbu = false;
                        }
                    }
                }while (!validCbu);
                
                int id=0;
                for (int i=0;i<cuentasBancarias.size();i++){
                    id = cuentasBancarias.get(i).getId()+1;
                }
                
                CuentaBancaria cuentaBancaria=new CuentaBancaria(id, idCliente, LocalDate.now(), 0, cbu, tipoCuenta);
                cuentasBancarias.add(cuentaBancaria);
                
                List<CuentaBancaria> cuentasCliente = clientes.get(posicionCliente).getCuentasBancarias();
                cuentasCliente.add(cuentaBancaria);
                clientes.get(posicionCliente).setCuentasBancarias(cuentasCliente);
                
                Banco.setCuentasBancarias(cuentasBancarias);
                Banco.setClientes(clientes);
                return "Cuenta bancaria creada";
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay clientes";
        }
    }

    public static Object mostrarCuentaBancaria(String idString) {
        cuentasBancarias=Banco.getCuentasBancarias();

        if (cuentasBancarias.size()!=0){
            if (ValidacionesEntradas.intValido(idString)) {
                int id=Integer.parseInt(idString);
                for (int i=0;i<cuentasBancarias.size();i++){
                    if (cuentasBancarias.get(i).getId()==id){
                        return cuentasBancarias.get(i);
                    }
                }
            }else{
                return "Error: ID invalido";
            }
        }else{
            return "Error: no hay cuentas bancarias";
        }
        return "Error: no existe la cuenta bancaria";
    }

    public static Object listarCuentasBancarias() {
        cuentasBancarias=Banco.getCuentasBancarias();
        
        if (cuentasBancarias.size()!=0){
            return cuentasBancarias;
        }else{
            return "Error: no hay cuentas bancarias";
        }
    }

    public static String eliminarCuentaBancaria(String idString) {
        clientes=Banco.getClientes();
        cuentasBancarias=Banco.getCuentasBancarias();
        
        if (cuentasBancarias.size()!=0) {
            if (ValidacionesEntradas.intValido(idString)) {
                int id=Integer.parseInt(idString);
                for (int i=0;i<cuentasBancarias.size();i++){
                    if (cuentasBancarias.get(i).getId()==id){
                        if (cuentasBancarias.get(i).getSaldo()>0) {
                            return "Error: la cuenta aun tiene saldo";
                        }else{
                            for (int k=0;k<clientes.size();k++){
                                if (clientes.get(k).getId()==cuentasBancarias.get(i).getIdCliente()) {
                                    List<CuentaBancaria> cuentaClientes = clientes.get(k).getCuentasBancarias();
                                    for(int j=0;j<cuentaClientes.size();j++){
                                        if (cuentaClientes.get(j).getId()==cuentasBancarias.get(i).getId()) {
                                            cuentaClientes.remove(j);
                                            clientes.get(k).setCuentasBancarias(cuentaClientes);
                                        }
                                    }
                                }
                            }
                            cuentasBancarias.remove(i);
                            Banco.setClientes(clientes);
                            Banco.setCuentasBancarias(cuentasBancarias);
                            return "Cuenta bancaria eliminada";
                        }
                    }
                }
            }else{
                return "Error: ID invalido";
            }
        }else{
            return "Error: no hay cuentas bancarias";
        }
        return "Error: no existe la cuenta bancaria";
    }
}
