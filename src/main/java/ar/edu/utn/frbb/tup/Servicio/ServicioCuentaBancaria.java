package ar.edu.utn.frbb.tup.Servicio;

import java.time.LocalDate;
import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioCuentaBancaria {
    public static String crearCuentaBancaria(String dniString, String tipoCuenta, String moneda) {
        if (ValidacionesCliente.dniValido(dniString) && ValidacionesCuentaBancaria.tipoCuentaValido(tipoCuenta) && ValidacionesCuentaBancaria.monedaValido(moneda)) {
            long dni=Long.parseLong(dniString);
            
            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++){
                    if (cuentasBancariasCliente.get(i).getTipoCuenta().equals(tipoCuenta) && cuentasBancariasCliente.get(i).getMoneda().equals(moneda)) {
                        return "Error: ya existe una cuenta del mismo tipo y moneda";
                    }    
                }
                
                boolean validCbu;
                String cbu;
                List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuentasBancarias();
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
                
                CuentaBancaria cuentaBancaria=new CuentaBancaria(id, cliente.getId(), LocalDate.now(), 0, cbu, tipoCuenta, moneda);
                cuentasBancarias.add(cuentaBancaria);
                DatosCuentaBancaria.setCuentasBancarias(cuentasBancarias);
                
                cuentasBancariasCliente.add(cuentaBancaria);
                cliente.setCuentasBancarias(cuentasBancariasCliente);
                
                return "Cuenta bancaria creada";
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static Object obtenerCuentaBancaria(String idString) {
        if (ValidacionesEntradas.intPositivoValido(idString)) {
            int id=Integer.parseInt(idString);
            
            CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(id);
            if (cuentaBancaria!=null) {
                return cuentaBancaria;
            }else{
                return "Error: la cuenta bancaria no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }

    public static Object listarCuentasBancarias() {
        List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuentasBancarias();
        if (cuentasBancarias.size()!=0){
            return cuentasBancarias;
        }else{
            return "Error: no hay cuentas bancarias";
        }
    }

    public static String eliminarCuentaBancaria(String idString) {
        if (ValidacionesEntradas.intPositivoValido(idString)) {
            int id=Integer.parseInt(idString);
    
            CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(id);
            if (cuentaBancaria!=null) {
                if (cuentaBancaria.getSaldo()==0) {
                    List<Movimiento> movimientosCuentaBancaria=cuentaBancaria.getMovimientos();
                    List<Movimiento> movimientos=DatosMovimiento.getMovimientos();
                    for(int i=0;i<movimientosCuentaBancaria.size();i++) {
                        movimientos.remove(movimientosCuentaBancaria.get(i));
                    }
                    DatosMovimiento.setMovimientos(movimientos);

                    Cliente cliente=DatosCliente.buscarClienteId(cuentaBancaria.getIdCliente());
                    
                    List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
                    cuentasBancariasCliente.remove(cuentaBancaria);
                    cliente.setCuentasBancarias(cuentasBancariasCliente);
    
                    List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuentasBancarias();
                    cuentasBancarias.remove(cuentaBancaria);
                    DatosCuentaBancaria.setCuentasBancarias(cuentasBancarias);
                    return "Cuenta bancaria eliminada";
                }else{
                    return "Error: la cuenta aun tiene saldo";
                }
            }else{
                return "Error: la cuenta bancaria no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }
}
