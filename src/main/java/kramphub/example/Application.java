package kramphub.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Array;

/**
 * Main class for the application
 */
@SpringBootApplication
public class Application {

    public static void main (String[] args){

        SpringApplication.run(Application.class, args);

    }
}
