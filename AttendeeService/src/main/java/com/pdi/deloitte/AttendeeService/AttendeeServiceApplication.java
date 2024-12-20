package com.pdi.deloitte.AttendeeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AttendeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendeeServiceApplication.class, args);
	}

}
