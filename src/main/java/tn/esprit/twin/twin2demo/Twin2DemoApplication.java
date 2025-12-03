package tn.esprit.twin.twin2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Twin2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Twin2DemoApplication.class, args);
    }

}
