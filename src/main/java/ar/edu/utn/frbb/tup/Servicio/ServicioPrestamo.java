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
        long dni=Integer.parseInt(dniString);
        int plazoMeses=Integer.parseInt(plazoMesesString);
        double montoPrestamo=Double.parseDouble(montoPrestamoString);
        double tasaInteresMensual=0.01;
        
        Cliente cliente=datosCliente.buscarClienteDni(dni);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el DNI ingresado");
        }

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

        if (cuentaBancaria==null) {
            throw new ExcepcionCuentaBancariaMonedaNoExiste("No existe una cuenta bancaria con la moneda ingresada");
        }

        String estadoPrestamo;
        String mensaje;
        
        if (servicioScoreCrediticio.scoreCrediticio(dni)) {
            double montoTotal=plazoMeses*montoPrestamo*tasaInteresMensual+montoPrestamo;
            double montoMensual=montoTotal/plazoMeses;
            
            List<Prestamo> prestamos=datosPrestamo.listarPrestamos();
            int id=0;
            for (int i=0;i<prestamos.size();i++) {
                id=prestamos.get(i).getId()+1;
            }

            Prestamo prestamo=new Prestamo(id,montoPrestamo,plazoMeses,0,montoPrestamo);
            
            datosPrestamo.agregarPrestamo(prestamo);

            List<Prestamo> prestamosCliente=cliente.getPrestamos();
            prestamosCliente.add(prestamo);
            cliente.setPrestamos(prestamosCliente);
            
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo()+montoPrestamo);

            List<Object> planPagos=new ArrayList<Object>();
            for (int j=0;j<plazoMeses;j++) {
                Map<String, Object> cuota=new LinkedHashMap<String, Object>();
                cuota.put("cuotaNro", j+1);
                cuota.put("monto", montoMensual);
                planPagos.add(cuota);
            }

            estadoPrestamo="Aprobado";
            mensaje="El prestamo fue acreditado en su cuenta bancaria (ID: "+cuentaBancaria.getId()+")";

            Map<String, Object> resultado=new LinkedHashMap<String, Object>();
            resultado.put("estado", estadoPrestamo);
            resultado.put("mensaje", mensaje);
            resultado.put("planPagos", planPagos);
            return resultado;
        }else{
            estadoPrestamo="Rechazado";
            mensaje="El prestamo fue debido a que su score crediticio es bajo";

            Map<String, Object> resultado=new LinkedHashMap<String, Object>();
            resultado.put("estado", estadoPrestamo);
            resultado.put("mensaje", mensaje);
            resultado.put("planPagos", "No hay plan de pagos");
            return resultado;
        }
    }

    public Map<String, Object> listarPrestamos(String idClienteString) throws ExcepcionClienteNoExiste, ExcepcionClienteNoTienePrestamo{
        int idCliente=Integer.parseInt(idClienteString);
        Cliente cliente=datosCliente.buscarClienteId(idCliente);
        if (cliente==null) {
            throw new ExcepcionClienteNoExiste("No existe un cliente con el ID ingresado");
        }

        if (cliente.getPrestamos().size()==0) {
            throw new ExcepcionClienteNoTienePrestamo("El cliente no tiene prestamos activos");
        }
        
        List<Prestamo> prestamosCliente=cliente.getPrestamos();
        Map<String, Object> prestamos=new LinkedHashMap<String, Object>();
        prestamos.put("numeroCliente", cliente.getDni());
        prestamos.put("prestamos", prestamosCliente);
        return prestamos;
    }
}
