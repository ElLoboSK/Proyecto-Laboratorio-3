package ar.edu.utn.frbb.tup.Servicio;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.time.LocalDate;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Movimiento;
import ar.edu.utn.frbb.tup.Persistencia.DatosCuentaBancaria;
import ar.edu.utn.frbb.tup.Persistencia.DatosMovimiento;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMismaCuentaBancaria;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionSaldoInsuficiente;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesOperacion.ExcepcionMonedaDiferente;

@Component
public class ServicioOperacion {
    private DatosMovimiento datosMovimiento;
    private DatosCuentaBancaria datosCuentaBancaria;

    public ServicioOperacion(DatosMovimiento datosMovimiento, DatosCuentaBancaria datosCuentaBancaria){
        this.datosMovimiento=datosMovimiento;
        this.datosCuentaBancaria=datosCuentaBancaria;
    }

    public Movimiento depositar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);

        //Se verifica que exista una cuenta bancaria con el ID ingresado. Si no existe, se lanza una excepcion.
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }

        //Se genera el ID del nuevo movimiento buscando el ID del ultimo movimiento y sumandole 1.
        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }

        //Se crea el movimiento y se agrega a la base de datos.
        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,LocalDate.now(),monto,"deposito");
        
        datosMovimiento.agregarMovimiento(movimiento);

        //Se agrega el movimiento a la cuenta bancaria y se actualiza el saldo.
        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() + monto;
        cuentaBancaria.setSaldo(saldoFinal);
        
        //Se retorna el movimiento creado.
        return movimiento;
    }

    public Movimiento retirar(String montoString, String idCuentaBancariaString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        double monto=Double.parseDouble(montoString);
        int idCuentaBancaria=Integer.parseInt(idCuentaBancariaString);
        
        //Se verifica que exista una cuenta bancaria con el ID ingresado. Si no existe, se lanza una excepcion.
        CuentaBancaria cuentaBancaria=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancaria);
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        //Se verifica que el saldo de la cuenta bancaria sea suficiente para realizar la operacion. Si no es suficiente, se lanza una excepcion.
        if (cuentaBancaria.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar la operacion");
        }

        //Se genera el ID del nuevo movimiento buscando el ID del ultimo movimiento y sumandole 1.
        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimiento=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimiento=movimientos.get(i).getId()+1;
        }

        //Se crea el movimiento y se agrega a la base de datos.
        Movimiento movimiento = new Movimiento(idMovimiento,idCuentaBancaria,LocalDate.now(),monto,"retiro");

        datosMovimiento.agregarMovimiento(movimiento);

        //Se agrega el movimiento a la cuenta bancaria y se actualiza el saldo.
        List<Movimiento> movimientosCuentaBancaria = cuentaBancaria.getMovimientos();
        movimientosCuentaBancaria.add(movimiento);
        cuentaBancaria.setMovimientos(movimientosCuentaBancaria);

        double saldoFinal = cuentaBancaria.getSaldo() - monto;
        cuentaBancaria.setSaldo(saldoFinal);

        //Se retorna el movimiento creado.
        return movimiento;
    }

    public List<Movimiento> transferir(String montoString, String idCuentaBancariaOrigenString, String idCuentaBancariaDestinoString) throws ExcepcionCuentaBancariaNoExiste, ExcepcionSaldoInsuficiente, ExcepcionMonedaDiferente, ExcepcionMismaCuentaBancaria{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        double monto=Double.parseDouble(montoString);
        int idCuentaBancariaOrigen=Integer.parseInt(idCuentaBancariaOrigenString);
        int idCuentaBancariaDestino=Integer.parseInt(idCuentaBancariaDestinoString);

        //Se verifica que el ID de la cuenta bancaria de origen y destino sean diferentes. Si no lo son, se lanza una excepcion.
        if (idCuentaBancariaOrigen==idCuentaBancariaDestino) {
            throw new ExcepcionMismaCuentaBancaria("La cuenta bancaria de origen y destino son la misma");
        }

        //Se verifica que exista una cuenta bancaria con el ID ingresado. Si no existe, se lanza una excepcion.
        CuentaBancaria cuentaBancariaOrigen=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaOrigen);
        CuentaBancaria cuentaBancariaDestino=datosCuentaBancaria.buscarCuentaBancariaId(idCuentaBancariaDestino);
        if (cuentaBancariaOrigen==null || cuentaBancariaDestino==null) {
            throw new ExcepcionCuentaBancariaNoExiste("No existe una cuenta bancaria con el ID ingresado");
        }
        
        //Se verifica que la moneda de la cuenta bancaria de origen y destino sean iguales. Si no lo son, se lanza una excepcion.
        if (!cuentaBancariaOrigen.getMoneda().equals(cuentaBancariaDestino.getMoneda())) {
            throw new ExcepcionMonedaDiferente("La moneda de la cuenta bancaria origen es diferente a la de la cuenta bancaria destino");
        }
        
        //Se verifica que el saldo de la cuenta bancaria de origen sea suficiente para realizar la operacion. Si no lo es, se lanza una excepcion.
        if (cuentaBancariaOrigen.getSaldo()<monto) {
            throw new ExcepcionSaldoInsuficiente("El saldo de la cuenta bancaria es insuficiente para realizar la operacion");
        }

        //Se genera el ID de los nuevos movimientos buscando el ID del ultimo movimiento y sumandole 1 al primer movimiento y 2 al segundo movimiento.
        List<Movimiento> movimientos=datosMovimiento.listarMovimientos();
        int idMovimientoOrigen=0;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoOrigen=movimientos.get(i).getId()+1;
        }
        int idMovimientoDestino=1;
        for(int i=0;i<movimientos.size();i++) {
            idMovimientoDestino=movimientos.get(i).getId()+2;
        }

        ///Se crea el movimiento de origen y destino y se agregan a la base de datos.
        Movimiento movimientoOrigen = new Movimiento(idMovimientoOrigen,idCuentaBancariaOrigen,LocalDate.now(),monto,"transferencia enviada");
        Movimiento movimientoDestino = new Movimiento(idMovimientoDestino,idCuentaBancariaDestino,LocalDate.now(),monto,"transferencia recibida");
        
        datosMovimiento.agregarMovimiento(movimientoOrigen);
        datosMovimiento.agregarMovimiento(movimientoDestino);

        //Se agrega cada movimiento a su respectiva cuenta bancaria y se actualiza el saldo de cada cuenta bancaria.
        List<Movimiento> movimientosCuentaBancariaOrigen = cuentaBancariaOrigen.getMovimientos();
        movimientosCuentaBancariaOrigen.add(movimientoOrigen);
        cuentaBancariaOrigen.setMovimientos(movimientosCuentaBancariaOrigen);

        List<Movimiento> movimientosCuentaBancariaDestino = cuentaBancariaDestino.getMovimientos();
        movimientosCuentaBancariaDestino.add(movimientoDestino);
        cuentaBancariaDestino.setMovimientos(movimientosCuentaBancariaDestino);

        double saldoFinalOrigen = cuentaBancariaOrigen.getSaldo() - monto;
        cuentaBancariaOrigen.setSaldo(saldoFinalOrigen);

        double saldoFinalDestino = cuentaBancariaDestino.getSaldo() + monto;
        cuentaBancariaDestino.setSaldo(saldoFinalDestino);

        //Se retorna la lista de movimientos de la transferencia.
        List<Movimiento> movimientosTransferencia = new ArrayList<Movimiento>();
        movimientosTransferencia.add(movimientoOrigen);
        movimientosTransferencia.add(movimientoDestino);

        return movimientosTransferencia;
    }
}