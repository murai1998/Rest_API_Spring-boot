package com.rest.demorest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@SpringBootApplication

public class DemoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestApplication.class, args);
	}
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

}
