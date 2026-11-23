package com.cjdc.jobportal.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cjdc.jobportal.common.exception.UnauthorizedException;
import com.cjdc.jobportal.user.dto.SignInRequest;
import com.cjdc.jobportal.user.dto.SignInResponse;
import com.cjdc.jobportal.user.entity.User;
import com.cjdc.jobportal.user.repository.UserRepository;
import com.cjdc.jobportal.user.security.JwtService;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	public SignInResponse signin(SignInRequest request) {
		String email = request.email().trim().toLowerCase();
		
		User user = userRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
		
		if(!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new UnauthorizedException("Invalid email or password");
		}
		
		String token = jwtService.generateToken(user);
		
		return new SignInResponse(token, "Bearer" , jwtService.getExpirationSec());		
	}
}
