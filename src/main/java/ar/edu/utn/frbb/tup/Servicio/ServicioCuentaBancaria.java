package ar.edu.utn.frbb.tup.Servicio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;

@Component
public class ServicioCuentaBancaria {
    private DatosCuentaBancaria datosCuentaBancaria;
    private DatosCliente datosCliente;
    private DatosMovimiento datosMovimiento;

    public ServicioCuentaBancaria(DatosCuentaBancaria datosCuentaBancaria, DatosCliente datosCliente, DatosMovimiento datosMovimiento){
        this.datosCuentaBancaria=datosCuentaBancaria;
        this.datosCliente=datosCliente;
        this.datosMovimiento=datosMovimiento;
    }

    public CuentaBancaria crearCuentaBancaria(String dniString, String tipoCuenta, String moneda) throws ExcepcionCuentaBancariaYaExiste, ExcepcionClienteNoExiste{
        long dni=Long.parseLong(dniString);
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        for (int i=0;i<cuentasBancariasCliente.size();i++){
            if (cuentasBancariasCliente.get(i).getTipoCuenta().equals(tipoCuenta) && cuentasBancariasCliente.get(i).getMoneda().equals(moneda)) {
                throw new ExcepcionCuentaBancariaYaExiste("El cliente ya tiene una cuenta del mismo tipo y moneda");
            }    
        }
        
        boolean validCbu;
        String cbu;
        List<CuentaBancaria> cuentasBancarias=datosCuentaBancaria.getCuentasBancarias();
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
        datosCuentaBancaria.setCuentasBancarias(cuentasBancarias);
        
        cuentasBancariasCliente.add(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancariasCliente);
        
        return cuentaBancaria;
    }

    public CuentaBancaria obtenerCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste{
        int id=Integer.parseInt(idString);
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        return cuentaBancaria;
    }

    public List<CuentaBancaria> listarCuentasBancarias() throws ExcepcionNoHayCuentasBancarias{
        List<CuentaBancaria> cuentasBancarias=datosCuentaBancaria.getCuentasBancarias();
        
        if (cuentasBancarias.size()==0){
            throw new ExcepcionNoHayCuentasBancarias("No hay cuentas bancarias registradas");
        }

        return cuentasBancarias;
    }

    public CuentaBancaria eliminarCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        int id=Integer.parseInt(idString);
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        if (cuentaBancaria.getSaldo()!=0) {
            throw new ExcepcionCuentaBancariaTieneSaldo("La cuenta bancaria aun tiene saldo");
        }

        List<Movimiento> movimientosCuentaBancaria=cuentaBancaria.getMovimientos();
        List<Movimiento> movimientos=datosMovimiento.getMovimientos();
        for(int i=0;i<movimientosCuentaBancaria.size();i++) {
            movimientos.remove(movimientosCuentaBancaria.get(i));
        }
        datosMovimiento.setMovimientos(movimientos);

        Cliente cliente=datosCliente.buscarClienteId(cuentaBancaria.getIdCliente());
        
        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        cuentasBancariasCliente.remove(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancariasCliente);

        List<CuentaBancaria> cuentasBancarias=datosCuentaBancaria.getCuentasBancarias();
        cuentasBancarias.remove(cuentaBancaria);
        datosCuentaBancaria.setCuentasBancarias(cuentasBancarias);
        
        return cuentaBancaria;
    }
}
