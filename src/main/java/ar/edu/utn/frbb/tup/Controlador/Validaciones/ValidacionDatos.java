package ar.edu.utn.frbb.tup.Controlador.Validaciones;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.Servicio.Excepciones.ExcepcionDatosInvalidos;

@Component
public class ValidacionDatos {
    public void intValido(String numeroString) throws ExcepcionDatosInvalidos{
        //Validacion de que el numero ingresado sea un entero, En caso de que no lo sea, se lanza una excepcion de datos invalidos.
        try{
            Integer.parseInt(numeroString);
        }catch(Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void doubleValido(String numeroString) throws ExcepcionDatosInvalidos{
        //Validacion de que el numero ingresado sea un double. En caso de que no lo sea, se lanza una excepcion de datos invalidos.
        try{
            Double.parseDouble(numeroString);
        }catch(Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void intPositivoValido(String numeroString) throws ExcepcionDatosInvalidos{
        //Validacion de que el numero ingresado sea un entero positivo. En caso de no ser entero, lanza una excepcion de datos invalidos.
        try{
            int numero=Integer.parseInt(numeroString);
            //Ademas tambien valida que el numero ingresado sea mayor a 0. Si no lo es, lanza una excepcion de datos invalidos.
            if (numero<0) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        }catch (Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }

    public void doublePositivoValido(String numeroString) throws ExcepcionDatosInvalidos{
        //Validacion de que el numero ingresado sea un double positivo. En caso de no ser double, lanza una excepcion de datos invalidos.
        try{
            double numero=Double.parseDouble(numeroString);
            //Ademas tambien valida que el numero ingresado sea mayor a 0. Si no lo es, lanza una excepcion de datos invalidos.
            if (numero<0) {
                throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
            }
        }catch (Exception e){
            throw new ExcepcionDatosInvalidos("Uno de los datos ingresados es invalido");
        }
    }
}
