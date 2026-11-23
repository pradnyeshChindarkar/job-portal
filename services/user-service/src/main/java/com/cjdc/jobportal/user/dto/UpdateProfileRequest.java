package com.cjdc.jobportal.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
		@NotBlank(message="Name is required") String name,
		@NotBlank(message="Phone is required") String phone,
		String image //optional
		) {

}
