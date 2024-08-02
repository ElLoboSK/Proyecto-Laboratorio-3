package ar.edu.utn.frbb.tup.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import ar.edu.utn.frbb.tup.Modelo.Banco;
import ar.edu.utn.frbb.tup.Modelo.Cliente;
import ar.edu.utn.frbb.tup.Modelo.CuentaBancaria;
import ar.edu.utn.frbb.tup.Modelo.Prestamo;
import ar.edu.utn.frbb.tup.Presentacion.ValidacionesEntradas;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCliente;
import ar.edu.utn.frbb.tup.Servicio.Validaciones.ValidacionesCuentaBancaria;

public class ServicioPrestamo {
    static List<Cliente> clientes=new ArrayList<Cliente>();
    static List<Prestamo> prestamos=new ArrayList<Prestamo>();

    public static Object solicitarPrestamo(String dniString, String plazoMesesString, String montoPrestamoString, String moneda){
        clientes=Banco.getClientes();
        prestamos=Banco.getPrestamos();

        if (clientes.size()!=0) {
            if (ValidacionesCliente.dniValido(dniString) && ValidacionesCuentaBancaria.monedaValido(moneda) && ValidacionesEntradas.intPositivoValido(plazoMesesString) && ValidacionesEntradas.doublePositivoValido(montoPrestamoString)) {
                int dni=Integer.parseInt(dniString);
                int plazoMeses=Integer.parseInt(plazoMesesString);
                double montoPrestamo=Double.parseDouble(montoPrestamoString);
                double tasaInteresMensual=0.01;
                for (int i=0;i<clientes.size();i++) {
                    if (clientes.get(i).getDni()==dni) {
                        if (clientes.get(i).getCuentasBancarias().size()!=0) {
                            int idCuentaCA=-1;
                            int idCuentaCC=-1;
                            List<CuentaBancaria> cuentasBancariasCliente=clientes.get(i).getCuentasBancarias();
                            for (int j=0;j<cuentasBancariasCliente.size();j++) {
                                if (cuentasBancariasCliente.get(j).getIdCliente()==clientes.get(i).getId()) {
                                    if (cuentasBancariasCliente.get(j).getMoneda().equals(moneda)) {
                                        if (cuentasBancariasCliente.get(j).getTipoCuenta().equals("Caja de ahorros")) {
                                            idCuentaCA=cuentasBancariasCliente.get(j).getId();
                                        }else if (cuentasBancariasCliente.get(j).getTipoCuenta().equals("Cuenta corriente")) {
                                            idCuentaCC=cuentasBancariasCliente.get(j).getId();
                                        }
                                    }
                                }
                            }
                            if (idCuentaCA!=-1 || idCuentaCC!=-1) {
                                String estadoPrestamo;
                                String mensaje;
                                
                                if (ServicioScoreCrediticio.scoreCrediticio(dni)) {
                                    int idCuenta=-1;
                                    if (idCuentaCA!=-1) {
                                        idCuenta=idCuentaCA;
                                    }else if (idCuentaCC!=-1) {
                                        idCuenta=idCuentaCC;
                                    }
                                    
                                    double montoTotal=plazoMeses*montoPrestamo*tasaInteresMensual+montoPrestamo;
                                    double montoMensual=montoTotal/plazoMeses;

                                    
                                    Prestamo prestamo=new Prestamo(montoPrestamo,plazoMeses,0,montoPrestamo);
                                    prestamos.add(prestamo);
                                    
                                    for (int j=0;j<cuentasBancariasCliente.size();j++) {
                                        if (idCuenta==cuentasBancariasCliente.get(j).getId()) {
                                            cuentasBancariasCliente.get(j).setSaldo(cuentasBancariasCliente.get(j).getSaldo()+montoPrestamo);
                                        }
                                    }
                                    
                                    List<Prestamo> prestamosCliente=clientes.get(i).getPrestamos();
                                    prestamosCliente.add(prestamo);
                                    clientes.get(i).setPrestamos(prestamosCliente);
                                    
                                    Banco.setPrestamos(prestamos);
                                    
                                    List<Object> planPagos=new ArrayList<Object>();
                                    for (int j=0;j<plazoMeses;j++) {
                                        Map<String, Object> cuota=new LinkedHashMap<String, Object>();
                                        cuota.put("cuotaNro", j+1);
                                        cuota.put("monto", montoMensual);
                                        planPagos.add(cuota);
                                    }

                                    estadoPrestamo="Aprobado";
                                    mensaje="El prestamo fue acreditado en su cuenta bancaria (ID: "+idCuenta+")";

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
                            return "Error: el cliente no tiene cuentas bancarias";
                        }
                    }
                }
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay clientes";
        }
        return "Error: el cliente no existe";
    }

    public static Object mostrarPrestamo(String idClienteString) {
        clientes=Banco.getClientes();

        if (clientes.size()!=0) {
            if (ValidacionesEntradas.intPositivoValido(idClienteString)) {
                int idCliente=Integer.parseInt(idClienteString);
                for (int i=0;i<clientes.size();i++) {
                    if (clientes.get(i).getId()==idCliente) {
                        if (clientes.get(i).getPrestamos().size()!=0) {
                            Map<String, Object> prestamos=new LinkedHashMap<String, Object>();
                            prestamos.put("numeroCliente", clientes.get(i).getDni());
                            prestamos.put("prestamos", clientes.get(i).getPrestamos());
                            return prestamos;
                        }else{
                            return "Error: el cliente no tiene prestamos";
                        }
                    }
                }
            }else{
                return "Error: datos invalidos";
            }
        }else{
            return "Error: no hay clientes";
        }
        return "Error: el cliente no existe";
    }
}
