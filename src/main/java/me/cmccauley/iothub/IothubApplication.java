package me.cmccauley.iothub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class IothubApplication {

	public static void main(String[] args) {
		SpringApplication.run(IothubApplication.class, args);
	}
}
