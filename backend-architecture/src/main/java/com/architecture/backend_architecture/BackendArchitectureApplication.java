package com.architecture.backend_architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.architecture.backend_architecture.repository")
@EntityScan(basePackages = "com.architecture.backend_architecture.model")
@ComponentScan(basePackages = "com.architecture.backend_architecture")
public class BackendArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendArchitectureApplication.class, args);
	}

}
