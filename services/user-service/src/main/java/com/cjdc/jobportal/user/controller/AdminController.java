package com.cjdc.jobportal.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjdc.jobportal.common.response.ApiResponse;
import com.cjdc.jobportal.user.dto.PageResponse;
import com.cjdc.jobportal.user.dto.UserResponse;
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
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
			@RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "10",name = "size") int size,
            @RequestParam(defaultValue = "id", name = "sbv") String sortBy,
            @RequestParam(defaultValue = "true", name = "sbd") boolean direction
			){
		PageResponse<UserResponse> response = userService.getAllResponse(page, size, sortBy, false);
		return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", response));
	}
}
