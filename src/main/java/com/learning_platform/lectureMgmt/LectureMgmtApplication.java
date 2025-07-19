package com.learning_platform.lectureMgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.learning_platform.configs.CorsConfig;
import com.learning_platform.configs.RestTemplateConfig;
import com.learning_platform.configs.SecurityConfig;
import com.learning_platform.filters.JWTFilter;
import com.learning_platform.filters.Auth.AuthSecurity;
import com.learning_platform.utils.CommonJwtUtils;

@SpringBootApplication
@EnableMethodSecurity
@Import({
		RestTemplateConfig.class,
		SecurityConfig.class,
		CorsConfig.class,
		JWTFilter.class,
		CommonJwtUtils.class,
		AuthSecurity.class
})
public class LectureMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectureMgmtApplication.class, args);
	}

}
