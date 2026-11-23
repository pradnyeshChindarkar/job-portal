package com.cjdc.jobportal.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjdc.jobportal.common.response.ApiResponse;
import com.cjdc.jobportal.user.dto.SignInRequest;
import com.cjdc.jobportal.user.dto.SignInResponse;
import com.cjdc.jobportal.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<SignInResponse>> login(@Valid @RequestBody SignInRequest signInRequest) {
		SignInResponse response = authService.signin(signInRequest);
		return ResponseEntity.ok(ApiResponse.success("Login successful", response));
	}
}
