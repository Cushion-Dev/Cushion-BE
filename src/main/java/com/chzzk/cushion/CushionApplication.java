package com.chzzk.cushion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CushionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CushionApplication.class, args);
	}

}
