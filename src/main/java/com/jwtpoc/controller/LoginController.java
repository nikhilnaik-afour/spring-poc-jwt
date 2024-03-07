package com.jwtpoc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwtpoc.config.CustomUserDetails;
import com.jwtpoc.dto.AuthRequest;
import com.jwtpoc.service.JwtService;


@RestController
public class LoginController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/customers")
	public List<String> getCustomers() {
		return List.of("c1", "c2", "c3", "c4");
	}
	
	@PostMapping("/login")
	public String authenticateAndGenerateToken(@RequestBody AuthRequest authRequest) {
		LOG.info("LOGGG: "+authRequest.getUsername());
		LOG.info("LOGGG: "+authRequest.getPassword());
		if (!authRequest.getUsername().equals(CustomUserDetails.getUsername()) || !authRequest.getPassword().equals(CustomUserDetails.getPassword())) {
			throw new RuntimeException("Username Password Invalid !");
		}
		return jwtService.generateToken(authRequest.getUsername());
	}
//	("/authenticate")
//	public SomeEnityData postMethodName(@RequestBody SomeEnityData entity) {
//		//TODO: process POST request
//		
//		return entity;
//	}
	

}
