package com.compassuol.sp.challenge.msordes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsordesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsordesApplication.class, args);
	}

}
