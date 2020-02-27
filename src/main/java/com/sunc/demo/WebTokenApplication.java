package com.sunc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class WebTokenApplication extends SpringBootServletInitializer {
	 
	  
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(WebTokenApplication.class);
	} 

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/WebToken");
		SpringApplication.run(WebTokenApplication.class, args);
	}

}
