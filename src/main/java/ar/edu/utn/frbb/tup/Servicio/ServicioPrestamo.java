package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoExiste;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCliente.ExcepcionClienteNoTienePrestamo;
import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionesCuentaBancaria.ExcepcionCuentaBancariaMonedaNoExiste;

@Component
public class ServicioPrestamo {
    private DatosPrestamo datosPrestamo;
    private DatosCliente datosCliente;
    private ServicioScoreCrediticio servicioScoreCrediticio;

    public ServicioPrestamo(DatosPrestamo datosPrestamo, DatosCliente datosCliente, ServicioScoreCrediticio servicioScoreCrediticio){
        this.datosPrestamo=datosPrestamo;
        this.datosCliente=datosCliente;
        this.servicioScoreCrediticio=servicioScoreCrediticio;
    }

    public Map<String, Object> solicitarPrestamo(String dniString, String plazoMesesString, String montoPrestamoString, String moneda) throws ExcepcionClienteNoExiste, ExcepcionCuentaBancariaMonedaNoExiste{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        long dni=Integer.parseInt(dniString);
        int plazoMeses=Integer.parseInt(plazoMesesString);
        double montoPrestamo=Double.parseDouble(montoPrestamoString);
        double tasaInteresMensual=0.01;
        
        //Se verifica que exista un cliente con el DNI ingresado. Si no existe, se lanza una excepcion.
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

        //Se busca entre las cuentas bancarias del cliente cual es la cuenta bancaria que se va a utilizar para el prestamo.
        CuentaBancaria cuentaBancaria=null;
        List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
        for (int i=0;i<cuentasBancariasCliente.size();i++) {
            if (cuentasBancariasCliente.get(i).getMoneda().equals(moneda)) {
                if (cuentasBancariasCliente.get(i).getTipoCuenta().equals("caja de ahorro")) {
                    cuentaBancaria=cuentasBancariasCliente.get(i);
                    break;
                }else if (cuentasBancariasCliente.get(i).getTipoCuenta().equals("cuenta corriente")) {
                    cuentaBancaria=cuentasBancariasCliente.get(i);
                }
            }
        }

        //Si no se encontro una cuenta bancaria con la moneda ingresada, se lanza una excepcion.
        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaMonedaNoExiste("No existe una cuenta bancaria con la moneda ingresada");
        }

        String estadoPrestamo;
        String mensaje;
        //En base al score crediticio del cliente, se determina si se aprueba o no el prestamo.
        if (servicioScoreCrediticio.scoreCrediticio(cliente.getDni())) {
            //Si se aprueba el prestamo, se calcula cuanto se debe pagar mensualmente.
            double montoTotal=plazoMeses*montoPrestamo*tasaInteresMensual+montoPrestamo;
            double montoMensual=montoTotal/plazoMeses;
            
            //Se genera el ID del prestamo buscando el ID del ultimo prestamo agregado y se le suma 1.
            List<Prestamo> prestamos=datosPrestamo.listarPrestamos();
            int id=0;
            for (int i=0;i<prestamos.size();i++) {
                id=prestamos.get(i).getId()+1;
            }

            //Se crea el prestamo y se agrega a la base de datos.
            Prestamo prestamo=new Prestamo(id,cliente.getId(),montoPrestamo,plazoMeses,0,montoPrestamo);
            
            datosPrestamo.agregarPrestamo(prestamo);

            //Se agrega el prestamo al cliente.
            List<Prestamo> prestamosCliente=cliente.getPrestamos();
            prestamosCliente.add(prestamo);
            cliente.setPrestamos(prestamosCliente);
            
            //Se actualiza el saldo de la cuenta bancaria.
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo()+montoPrestamo);

            ///Se crea el plan de pagos del prestamo.
            List<Object> planPagos=new ArrayList<Object>();
            for (int j=0;j<plazoMeses;j++) {
                Map<String, Object> cuota=new LinkedHashMap<String, Object>();
                cuota.put("cuotaNro", j+1);
                cuota.put("monto", montoMensual);
                planPagos.add(cuota);
            }

            //Se crea el resultado para retornar con los datos del prestamo.
            estadoPrestamo="Aprobado";
            mensaje="El prestamo fue acreditado en su cuenta bancaria (ID: "+cuentaBancaria.getId()+")";

            Map<String, Object> resultado=new LinkedHashMap<String, Object>();
            resultado.put("estado", estadoPrestamo);
            resultado.put("mensaje", mensaje);
            resultado.put("planPagos", planPagos);

            return resultado;
        }else{
            //Si el prestamo no se aprueba, se crea un plan de pagos vacio y se retorna el resultado.
            estadoPrestamo="Rechazado";
            mensaje="El prestamo fue rechazado debido a que su score crediticio es bajo";

            Map<String, Object> resultado=new LinkedHashMap<String, Object>();
            resultado.put("estado", estadoPrestamo);
            resultado.put("mensaje", mensaje);
            resultado.put("planPagos", "No hay plan de pagos");

            return resultado;
        }
    }

    public Map<String, Object> listarPrestamos(String idClienteString) throws ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        //Se pasan los datos a el tipo de dato corresponiente para trabajar con ellos.
        int idCliente=Integer.parseInt(idClienteString);
        //Se verifica que exista un cliente con el ID ingresado. Si no existe, se lanza una excepcion.
        Cliente cliente=datosCliente.buscarClienteId(idCliente);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el ID ingresado");
        }

        //Se verifica que el cliente tenga prestamos activos. Si no tiene, se lanza una excepcion.
        if (cliente.getPrestamos().size()==0) {
            throw new ExcepcionClienteNoTienePrestamo("El cliente no tiene prestamos activos");
        }
        
        //En caso de tener prestamos activos, se arma un resutlado con la lista de prestamos del cliente y se retorna.
        List<Prestamo> prestamosCliente=cliente.getPrestamos();
        Map<String, Object> prestamos=new LinkedHashMap<String, Object>();
        prestamos.put("numeroCliente", cliente.getDni());
        prestamos.put("prestamos", prestamosCliente);
        return prestamos;
    }
}
