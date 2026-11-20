package com.cjdc.jobportal.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cjdc.jobportal.common.exception.BadRequest;
import com.cjdc.jobportal.common.exception.ConflictException;
import com.cjdc.jobportal.user.dto.SignupRequest;
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

		return new UserResponse(savedUser.getId(), savedUser.getFullname(), savedUser.getEmail(), savedUser.getPhone(),
				savedUser.getRole());
	}
}
