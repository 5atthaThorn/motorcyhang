package com.ruayshop.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.ruayshop.Controllers", "com.ruayshop.Entities", "com.ruayshop.Services"})
@EnableJpaRepositories(basePackages = "com.ruayshop.Repositories")
@EntityScan(basePackages = "com.ruayshop.Entities")

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
