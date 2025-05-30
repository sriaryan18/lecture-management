package com.learning_platform.lectureMgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class LectureMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectureMgmtApplication.class, args);
	}

}
