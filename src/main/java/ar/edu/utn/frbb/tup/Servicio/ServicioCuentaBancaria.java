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
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Long.parseLong(dniString);
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        //Se verifica que exista un cliente con el DNI ingresado. Si no existe, se lanza una excepcion.
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        //Se verifica que no exista una cuenta bancaria del mismo tipo y moneda para el cliente. Si existe, se lanza una excepcion.
        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        for (int i=0;i<cuentasBancariasCliente.size();i++){
            if (cuentasBancariasCliente.get(i).getTipoCuenta().equals(tipoCuenta) && cuentasBancariasCliente.get(i).getMoneda().equals(moneda)) {
                throw new ExcepcionCuentaBancariaYaExiste("El cliente ya tiene una cuenta del mismo tipo y moneda");
            }    
        }
        
        //Se obtiene el CBU de la cuenta bancaria randomizando los numeros entre 100000 y 999999.
        boolean validCbu;
        String cbu;
        List<CuentaBancaria> cuentasBancarias=datosCuentaBancaria.listarCuentasBancarias();
        do{
            cbu=Math.round(Math.random()*(999999 - 100000 + 1) + 100000)+"";
            validCbu = true;
            for (int j=0;j<cuentasBancarias.size();j++){
                if (cuentasBancarias.get(j).getCbu().equals(cbu)){
                    validCbu = false;
                }
            }
        }while (!validCbu);
        
        //Se busca entre las cuentas bancarias registradas cual es el ID mayor y se le suma 1 para obtener el ID de la nueva cuenta bancaria.
        int id=0;
        for (int i=0;i<cuentasBancarias.size();i++){
            id = cuentasBancarias.get(i).getId()+1;
        }
        
        //Se crea la cuenta bancaria y se agrega a la base de datos.
        CuentaBancaria cuentaBancaria=new CuentaBancaria(id, cliente.getId(), LocalDate.now(), 0, cbu, tipoCuenta, moneda);
        datosCuentaBancaria.agregarCuentaBancaria(cuentaBancaria);
        
        //Se agrega la cuenta bancaria al cliente.
        cuentasBancariasCliente.add(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancariasCliente);
        
        //Se retorna la cuenta bancaria creada.
        return cuentaBancaria;
    }

    public CuentaBancaria obtenerCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        int id=Integer.parseInt(idString);
        //Se verifica que exista una cuenta bancaria con el ID ingresado. Si no existe, se lanza una excepcion.
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        ///Se retorna la cuenta bancaria encontrada.
        return cuentaBancaria;
    }

    public List<CuentaBancaria> listarCuentasBancarias() throws ExcepcionNoHayCuentasBancarias{
        //Se obtienen las cuentas bancarias registradas.
        List<CuentaBancaria> cuentasBancarias=datosCuentaBancaria.listarCuentasBancarias();
        
        //Se verifica que existan cuentas bancarias registradas. Si no existen, se lanza una excepcion.
        if (cuentasBancarias.size()==0){
            throw new ExcepcionNoHayCuentasBancarias("No hay cuentas bancarias registradas");
        }

        //Se retorna la lista de cuentas bancarias.
        return cuentasBancarias;
    }

    public CuentaBancaria eliminarCuentaBancaria(String idString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionCuentaBancariaTieneSaldo{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        int id=Integer.parseInt(idString);
        //Se verifica que exista una cuenta bancaria con el ID ingresado. Si no existe, se lanza una excepcion.
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(id);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        //Se verifica que la cuenta bancaria no tenga saldo. Si tiene saldo, se lanza una excepcion.
        if (cuentaBancaria.getSaldo()!=0) {
            throw new ExcepcionCuentaBancariaTieneSaldo("La cuenta bancaria aun tiene saldo");
        }

        //Se eliminan los movimientos de la cuenta bancaria.
        List<Movimiento> movimientosCuentaBancaria=cuentaBancaria.getMovimientos();
        for(int i=0;i<movimientosCuentaBancaria.size();i++) {
            datosMovimiento.eliminarMovimiento(movimientosCuentaBancaria.get(i));
        }

        //Se elimina la cuenta bancaria de la lista de cuentas bancarias del cliente.
        Cliente cliente=datosCliente.buscarClienteId(cuentaBancaria.getIdCliente());
        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        cuentasBancariasCliente.remove(cuentaBancaria);
        cliente.setCuentasBancarias(cuentasBancariasCliente);

        //Se elimina la cuenta bancaria de la base de datos.
        datosCuentaBancaria.eliminarCuentaBancaria(cuentaBancaria);
        
        //Se retorna la cuenta bancaria eliminada.
        return cuentaBancaria;
    }
}
