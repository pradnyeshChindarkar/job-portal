package com.cjdc.jobportal.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjdc.jobportal.common.response.ApiResponse;
import com.cjdc.jobportal.user.dto.SignupRequest;
import com.cjdc.jobportal.user.dto.UpdateProfileRequest;
import com.cjdc.jobportal.user.dto.UserResponse;
import com.cjdc.jobportal.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody @Valid SignupRequest request) {
		UserResponse response = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("User Registered successfully", response));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(Authentication authentication) {
		UserResponse response = userService.getCurrentUserProfile(authentication);
		return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", response));
	}

	@PutMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(Authentication authentication,
			@Valid @RequestBody UpdateProfileRequest request) {
		UserResponse response = userService.updateCurrenUserProfile(authentication, request);
		return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", response));
	}
	
}