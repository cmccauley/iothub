package me.cmccauley.iothub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class IothubApplication {

    public static void main(String[] args) {
        SpringApplication.run(IothubApplication.class, args);
    }
}
