package com.dsa.tabidabi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TabidabiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabidabiApplication.class, args);
		
		
	}

}
