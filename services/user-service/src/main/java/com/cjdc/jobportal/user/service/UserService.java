package com.cjdc.jobportal.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cjdc.jobportal.common.exception.BadRequest;
import com.cjdc.jobportal.common.exception.ConflictException;
import com.cjdc.jobportal.common.exception.UnauthorizedException;
import com.cjdc.jobportal.user.dto.SignupRequest;
import com.cjdc.jobportal.user.dto.UpdateProfileRequest;
import com.cjdc.jobportal.user.dto.UserResponse;
import com.cjdc.jobportal.user.entity.Role;
import com.cjdc.jobportal.user.entity.User;
import com.cjdc.jobportal.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse createUser(SignupRequest request) {
		if (request.role().equals(Role.ADMIN)) {
			throw new BadRequest("User cannot directly assign a ADMIN role");
		}

		String email = request.email().trim().toLowerCase();
		if (repository.existsByEmailIgnoreCase(email)) {
			throw new ConflictException("User already exists with email: " + email);
		}

		User user = new User();
		user.setEmail(email);
		user.setFullname(request.name());
		user.setPhone(request.phone());
		user.setRole(request.role());
		user.setPassword(passwordEncoder.encode(request.password()));
		User savedUser = repository.save(user);

		return toUserResponse(savedUser);
	}

	public UserResponse getCurrentUserProfile(Authentication authentication) {
		User user = resolveAuthenticatedUser(authentication);
		return toUserResponse(user);
	}

	public UserResponse updateCurrenUserProfile(Authentication authentication, UpdateProfileRequest request) {
		User user = resolveAuthenticatedUser(authentication);
		user.setFullname(request.name().trim());
		user.setPhone(request.phone().trim());
		if (request.image() != null && !request.image().isBlank()) {
			user.setProfile_image_url(request.image().trim());
		}
//		user.setProfile_image_url(request.image()!=null? request.image().trim():null);

		User savedUser = repository.save(user);
		return toUserResponse(savedUser);
	}

	private User resolveAuthenticatedUser(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
			throw new UnauthorizedException("Unauthorized access");
		}
		String email = authentication.getName().trim().toLowerCase();
		return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new UnauthorizedException("User not found"));
	}

	private UserResponse toUserResponse(User user) {
		return new UserResponse(user.getId(), user.getFullname(), user.getEmail(), user.getPhone(),
				user.getProfile_image_url(), user.getRole(), user.getCreatedAt());
	}

}
