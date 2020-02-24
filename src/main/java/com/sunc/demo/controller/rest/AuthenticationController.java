package com.sunc.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunc.demo.model.Request;
import com.sunc.demo.model.Response;
import com.sunc.demo.service.JWTUserDetailService;
import com.sunc.demo.service.utils.TokenUtil;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenUtil jwtutil;
	
	@Autowired
	private JWTUserDetailService userService;
	
	@RequestMapping(name = "authenticate" ,method = RequestMethod.POST ,value = "/api/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody  Request request){
	
		manager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUserName(), request.getPassword())
				);
		
		final UserDetails userdetails=userService.loadUserByUsername(request.getUserName());
		
		final String token=jwtutil.generateToken(userdetails);
		return ResponseEntity.ok(new Response().setToken(token).getToken());
	}
	
	@RequestMapping(name = "sayHello" ,method = RequestMethod.GET ,value = "/api/sayHello/{param}")
	public ResponseEntity<?> sayHello(@PathVariable("param")  String param){
	
	  
		System.out.println("Hell");
		return ResponseEntity.ok("OK");
	}
	
}
