package dev.cher.santa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantaApplication.class, args);
	}

}
