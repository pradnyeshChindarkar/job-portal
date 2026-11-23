package com.cjdc.jobportal.user.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjdc.jobportal.user.dto.SignInRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;

	@PostMapping("/generateToken")
	public String generateToken(@RequestBody SignInRequest signInRequest) {
		Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password())
				);
		if(authentication.isAuthenticated()) {
			
		}
	}
}
