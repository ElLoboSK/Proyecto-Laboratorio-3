package ar.edu.utn.frbb.tup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Pendiente:
//Testear controladores y servicios
//Usar excepcion de datos en el controlador, no en el servicio
//Convertir clases de servicio y persistencia en no estaticas

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}