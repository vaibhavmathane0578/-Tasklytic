package com.tasklytic.shared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tasklytic.shared")
public class SharedLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharedLibraryApplication.class, args);
	}

}
