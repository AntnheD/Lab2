package com.ctbe.product_service;

import com.ctbe.product_service.model.Product;
import com.ctbe.product_service.repository.ProductRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Product Service API",
        version = "1.0.0",
        description = "RESTful Product Catalogue — Lab 2"
))
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(ProductRepository repo) {
		return args -> {
			repo.save(new Product("Laptop", 1200.00, 10, "Electronics"));
			repo.save(new Product("Monitor", 350.00, 25, "Electronics"));
			repo.save(new Product("Keyboard", 85.00, 50, "Accessories"));
		};
	}
}