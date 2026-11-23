package com.cjdc.jobportal.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjdc.jobportal.common.response.ApiResponse;
import com.cjdc.jobportal.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {

	private final UserService userService;
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") long id, Authentication authentication){
		userService.deleteByUserId(id, authentication);
		return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
	}	
}
