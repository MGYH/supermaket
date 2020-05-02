package com.mgyh.supermaket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.mgyh.supermaket")
public class SupermaketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermaketApplication.class, args);
	}

}
