package kramphub.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stagemonitor.core.Stagemonitor;

/**
 * Main class for the application
 */
@SpringBootApplication
public class Application {

    public static void main (String[] args){

        Stagemonitor.init();
        SpringApplication.run(Application.class, args);

    }
}
