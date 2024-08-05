package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Persistencia.DatosCliente;
import ar.edu.utn.frbb.tup.Persistencia.DatosPrestamo;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioPrestamo {
    public static Object solicitarPrestamo(String dniString, String plazoMesesString, String montoPrestamoString, String moneda){
        if (ValidacionesCliente.dniValido(dniString) && ValidacionesCuentaBancaria.monedaValido(moneda) && ValidacionesEntradas.intPositivoValido(plazoMesesString) && ValidacionesEntradas.doublePositivoValido(montoPrestamoString)) {
            long dni=Integer.parseInt(dniString);
            int plazoMeses=Integer.parseInt(plazoMesesString);
            double montoPrestamo=Double.parseDouble(montoPrestamoString);
            double tasaInteresMensual=0.01;

            Cliente cliente=DatosCliente.buscarClienteDni(dni);
            if (cliente!=null) {
                CuentaBancaria cuentaBancaria=null;
                List<CuentaBancaria> cuentasBancariasCliente=cliente.getCuentasBancarias();
                for (int i=0;i<cuentasBancariasCliente.size();i++) {
                    if (cuentasBancariasCliente.get(i).getMoneda().equals(moneda)) {
                        if (cuentasBancariasCliente.get(i).getTipoCuenta().equals("Caja de ahorro")) {
                            cuentaBancaria=cuentasBancariasCliente.get(i);
                            break;
                        }else if (cuentasBancariasCliente.get(i).getTipoCuenta().equals("Cuenta corriente")) {
                            cuentaBancaria=cuentasBancariasCliente.get(i);
                        }
                    }
                }
                if (cuentaBancaria!=null) {
                    String estadoPrestamo;
                    String mensaje;
                    
                    if (ServicioScoreCrediticio.scoreCrediticio(dni)) {
                        double montoTotal=plazoMeses*montoPrestamo*tasaInteresMensual+montoPrestamo;
                        double montoMensual=montoTotal/plazoMeses;
                        
                        List<Prestamo> prestamos=DatosPrestamo.getPrestamos();
                        int id=0;
                        for (int i=0;i<prestamos.size();i++) {
                            id=prestamos.get(i).getId()+1;
                        }

                        Prestamo prestamo=new Prestamo(id,montoPrestamo,plazoMeses,0,montoPrestamo);
                        
                        prestamos.add(prestamo);
                        DatosPrestamo.setPrestamos(prestamos);

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
                }else{
                    return "Error: el cliente no tiene cuenta bancaria del tipo de moneda solicitada";
                }
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        } 
    }

    public static Object mostrarPrestamo(String idClienteString) {
        if (ValidacionesEntradas.intPositivoValido(idClienteString)) {
            int idCliente=Integer.parseInt(idClienteString);
            
            Cliente cliente=DatosCliente.buscarClienteId(idCliente);
            if (cliente!=null) {
                if (cliente.getPrestamos().size()!=0) {
                    List<Prestamo> prestamosCliente=cliente.getPrestamos();
                    Map<String, Object> prestamos=new LinkedHashMap<String, Object>();
                    prestamos.put("numeroCliente", cliente.getDni());
                    prestamos.put("prestamos", prestamosCliente);
                    return prestamos;
                }else{
                    return "Error: el cliente no tiene prestamos";
                }
            }else{
                return "Error: el cliente no existe";
            }
        }else{
            return "Error: datos invalidos";
        }
    }
}
