package ar.edu.utn.frbb.tup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Pendiente:
//Ajustar coidgos de error
//Analizar tema mocking: al usar clases estaticas, no se puede crear una instancia de las mismas y por lo tanto no se puede usar mocks

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}