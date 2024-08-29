package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatos {
    public void intValido(String numeroString) throws ExcepcionDatosInvalidos{
        try{
            Integer.parseInt(numeroString);
        }catch(Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void doubleValido(String numeroString) throws ExcepcionDatosInvalidos{
        try{
            Double.parseDouble(numeroString);
        }catch(Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void intPositivoValido(String numeroString) throws ExcepcionDatosInvalidos{
        try{
            int numero=Integer.parseInt(numeroString);
            if (numero<0) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        }catch (Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void doublePositivoValido(String numeroString) throws ExcepcionDatosInvalidos{
        try{
            double numero=Double.parseDouble(numeroString);
            if (numero<0) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        }catch (Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }
}
