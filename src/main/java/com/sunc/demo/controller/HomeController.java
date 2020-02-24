package com.sunc.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	@GetMapping(name = "hello", path = "/hello")
	public String sayHello() {
		return "Hellu";
	}
}
