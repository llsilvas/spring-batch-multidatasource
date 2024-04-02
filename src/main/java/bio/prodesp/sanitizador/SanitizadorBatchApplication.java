package bio.prodesp.sanitizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class SanitizadorBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanitizadorBatchApplication.class, args);
    }

}
