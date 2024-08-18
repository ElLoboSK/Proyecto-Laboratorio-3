package ar.edu.utn.frbb.tup.Servicio;

import java.time.LocalDate;
import java.util.List;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaTieneSaldo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaYaExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionNoHayCuentasBancarias;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesDatos;

public class ServicioCuentaBancaria {
    public static CuentaBancaria crearCuentaBancaria(String dniString, String tipoCuenta, String moneda) throws ExcepcionCuentaBancariaYaExiste, ExcepcionDatosInvalidos, ExcepcionClienteNoExiste{
        if (!ValidacionesCliente.dniValido(dniString) || !ValidacionesCuentaBancaria.tipoCuentaValido(tipoCuenta) || !ValidacionesCuentaBancaria.monedaValido(moneda)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");    
        }

        long dni=Long.parseLong(dniString);
        Cliente cliente=DatosCliente.buscarClienteDni(dni);
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
        
        return cuentaBancaria;
    }

    public static CuentaBancaria obtenerCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos{
        if (!ValidacionesDatos.intPositivoValido(idString)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }

        int id=Integer.parseInt(idString);
        CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        return cuentaBancaria;
    }

    public static List<CuentaBancaria> listarCuentasBancarias() throws ExcepcionNoHayCuentasBancarias{
        List<CuentaBancaria> cuentasBancarias=DatosCuentaBancaria.getCuentasBancarias();
        
        if (cuentasBancarias.size()==0){
            throw new ExcepcionNoHayCuentasBancarias("No hay cuentas bancarias registradas");
        }

        return cuentasBancarias;
    }

    public static CuentaBancaria eliminarCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionDatosInvalidos, ExcepcionCuentaBancariaTieneSaldo{
        if (!ValidacionesDatos.intPositivoValido(idString)) {
            throw new ExcepcionDatosInvalidos("Un dato ingresado es invalido");
        }
        
        int id=Integer.parseInt(idString);
        CuentaBancaria cuentaBancaria=DatosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        if (cuentaBancaria.getSaldo()!=0) {
            throw new ExcepcionCuentaBancariaTieneSaldo("La cuenta bancaria aun tiene saldo");
        }

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
        
        return cuentaBancaria;
    }
}
